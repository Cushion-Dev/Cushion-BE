package com.chzzk.cushion.style.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ClovaStudioApiExecutor {

    @Value("${ncp.clova.studio.apiUrl}")
    private String apiUrl;

    @Value("${ncp.clova.studio.apiKey}")
    private String apiKey;

    @Value("${ncp.clova.studio.apiKeyPrimaryVal}")
    private String apiKeyPrimaryVal;

    @Value("${ncp.clova.studio.requestId}")
    private String requestId;

    public String execute(JSONObject executeRequest) {
        try {
            StopWatch totalTime = start("total time");

            // API 요청 전송
            StopWatch apiRequestStopWatch = start("api request time");
            URL url = new URL(apiUrl);
            HttpURLConnection connection = createRequestHeader(url);
            request(connection, executeRequest);
            stop(apiRequestStopWatch);

            // API 응답 수신
            StopWatch apiResponseStopWatch = start("api response time");
            String responseData = getResponseData(connection);
            stop(apiResponseStopWatch);

            // 데이터 파싱
            StopWatch dataParsingStopWatch = start("data parsing time");
            String result = parseResponseData(responseData);

            stop(dataParsingStopWatch);

            stop(totalTime);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpURLConnection createRequestHeader(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setReadTimeout(50000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-NCP-CLOVASTUDIO-API-KEY", this.apiKey);
        connection.setRequestProperty("X-NCP-APIGW-API-KEY", this.apiKeyPrimaryVal);
        connection.setRequestProperty("X-NCP-CLOVASTUDIO-REQUEST-ID", this.requestId);
        connection.setRequestProperty("Accept", "text/event-stream");
        return connection;
    }

    private void request(HttpURLConnection connection, JSONObject executeRequest) throws IOException {
        connection.getOutputStream().write(executeRequest.toString().getBytes(StandardCharsets.UTF_8));
    }

    private String getResponseData(HttpURLConnection connection) throws IOException {
        StopWatch responseCheckingStopWatch = start("response checking time");
        BufferedReader reader = checkResponse(connection);
        stop(responseCheckingStopWatch);

        StopWatch responseReadingStopWatch = start("response reading time");
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        stop(responseReadingStopWatch);

        return response.toString();
    }

    private BufferedReader checkResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            return new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        log.error("response code = {}", responseCode);
        return new BufferedReader(new InputStreamReader(connection.getErrorStream()));
    }

    private String parseResponseData(String responseData) throws JsonProcessingException {
        log.info("response data = {}", responseData);
        ObjectMapper objectMapper = new ObjectMapper();
        String[] parts = responseData.split("id:");

        for (String part : parts) {
            // part = 6ca9f586-e7e8-4baf-8e24-c1d34b39aa26event:tokendata:{"message":{"role":"assistant","content":"변환"},"index":0,"inputLength":646,"outputLength":1,"stopReason":null}
            if (!part.trim().isEmpty()) {
                String[] eventAndData = part.split("event:")[1].trim().split("data:", 2);
                String event = eventAndData[0].trim();
                String data = eventAndData[1].trim();

                if (event.equals("result")) {
                    JsonNode rootNode = objectMapper.readTree(data);
                    JsonNode messageNode = rootNode.get("message");
                    if (messageNode != null) {
                        String resultContent = messageNode.get("content").asText();
                        String substring = resultContent.substring(0, 4);
                        if (substring.equals("변환: ")) {
                            return resultContent.replace(substring, "");
                        }
                        return resultContent;
                    }
                }
            }
        }

        return "NONE";
    }

    private StopWatch start(String watchName) {
        StopWatch stopWatch = new StopWatch(watchName);
        stopWatch.start();
        return stopWatch;
    }

    private void stop(StopWatch stopWatch) {
        stopWatch.stop();
        log.info("{}(ms) : {}", stopWatch.getId(), stopWatch.getTotalTimeMillis());
    }
}
