package com.chzzk.cushion.style.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class ClovaOcrApiExecutor {

    @Value("${ncp.clova.ocr.apiUrl}")
    private String apiUrl;

    @Value("${ncp.clova.ocr.secretKey}")
    private String secretKey;

    public String execute(List<MultipartFile> multipartFiles) {
        List<String> result = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                URL url = new URL(apiUrl);
                String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
                HttpURLConnection connection = createRequestHeader(url, boundary);
                request(connection, multipartFile, boundary);
                String responseData = getResponseData(connection);
                String parsed = parseResponseData(responseData);
                result.add(parsed);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (String s : result) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    private HttpURLConnection createRequestHeader(URL url, String boundary) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setReadTimeout(50000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setRequestProperty("X-OCR-SECRET", this.secretKey);
        return connection;
    }

    private void request(HttpURLConnection connection, MultipartFile multipartFile, String boundary)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("version", "V2");
        json.put("requestId", UUID.randomUUID().toString());
        json.put("timestamp", System.currentTimeMillis());

        ArrayNode images = mapper.createArrayNode();
        ObjectNode image = mapper.createObjectNode();
        String contentType = multipartFile.getContentType();
        if (Objects.equals(contentType, "image/png")) {
            image.put("format", "png");
        } else if (Objects.equals(contentType, "image/jpeg")) {
            image.put("format", "jpeg");
        } else if (Objects.equals(contentType, "image/tiff")) {
            image.put("format", "tiff");
        }
        image.put("name", multipartFile.getName());

        images.add(image);

        json.set("images", images);

        connection.connect();
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        File tempFile = File.createTempFile("temp", multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        writeMultiPartFile(outputStream, json, tempFile, boundary);
        outputStream.close();

        connection.getOutputStream().write(json.toString().getBytes(StandardCharsets.UTF_8));
    }

    private void writeMultiPartFile(DataOutputStream outputStream, ObjectNode json, File file, String boundary)
            throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(json.toString());
        sb.append("\r\n");

        outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        if (file != null && file.isFile()) {
            outputStream.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));
            StringBuffer fileString = new StringBuffer();
            fileString.append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"").append(file.getName()).append("\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            outputStream.write(fileString.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
                outputStream.write("\r\n".getBytes());
            }

        }

        outputStream.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }

    private String getResponseData(HttpURLConnection connection) throws IOException {
        BufferedReader reader = checkResponse(connection);

        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

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

    private String parseResponseData(String responseData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(responseData);
            JsonNode fieldsNode = rootNode.path("images").get(0).path("fields");
            StringBuffer result = new StringBuffer();
            for (JsonNode field : fieldsNode) {
                String inferText = field.path("inferText").asText();
                boolean isLineBreak = field.path("lineBreak").asBoolean();
                if (isLineBreak) {
                    result.append(inferText).append("\n");
                    continue;
                }
                result.append(inferText).append(" ");
            }

            if (result.length() > 0) {
                result.setLength(result.length() - 1);
            }

            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
