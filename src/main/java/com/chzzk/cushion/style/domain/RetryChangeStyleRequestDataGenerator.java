package com.chzzk.cushion.style.domain;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.prompt.domain.Prompt;
import com.chzzk.cushion.prompt.domain.PromptReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

import static com.chzzk.cushion.prompt.domain.PromptType.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryChangeStyleRequestDataGenerator {

    private static final String RETRY_MESSAGE = "이전에 변환했던 문장과 다른 말투로 다시 변환해 주세요";

    private final PromptReader promptReader;

    public JSONObject generate(Member member, String input, String prevAnswer, ChatRoom chatRoom, boolean withPersonality) {
        Prompt prompt = withPersonality ?
                promptReader.readPrompt(CHANGE_STYLE_WITH_PERSONALITY) : promptReader.readPrompt(CHANGE_STYLE);
        String promptSystemMessage = prompt.getSystemMessage();

        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", promptSystemMessage);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", createUserMessageForChangingStyle(member, chatRoom, input, withPersonality));

        JSONObject assistant = new JSONObject();
        assistant.put("role", "assistant");
        assistant.put("content", prevAnswer);

        JSONObject retry = new JSONObject();
        retry.put("role", "user");
        retry.put("content", RETRY_MESSAGE);

        return getRequestData(system, user, assistant, retry);
    }

    private String createUserMessageForChangingStyle(Member member, ChatRoom chatRoom, String userMessage,
                                                     boolean withPersonality) {
        StringBuffer sb = new StringBuffer();
        sb.append("사용자 이름: ").append(member.getRealName()).append("\n");
        sb.append("사용자 소속: ").append(member.getAffiliation()).append("\n");
        sb.append("사용자 직무: ").append(member.getJob()).append("\n");
        sb.append("상대방 이름: ").append(chatRoom.getPartnerName()).append("\n");
        sb.append("상대방 관계: ").append(chatRoom.getPartnerRel().getLabel()).append("\n");
        if (withPersonality) {
            sb.append("상대방 성격: ").append(userMessage).append("\n");
        }
        sb.append("문장: ").append(userMessage).append("\n");
        sb.append("변환: ");
        return sb.toString();
    }

    private JSONObject getRequestData(JSONObject system, JSONObject user, JSONObject assistant, JSONObject retry) {
        JSONArray presetText = new JSONArray();
        presetText.add(system);
        presetText.add(user);
        presetText.add(assistant);
        presetText.add(retry);

        JSONObject requestData = new JSONObject();
        requestData.put("messages", presetText);
        requestData.put("topP", 0.8);
        requestData.put("topK", 0);
        requestData.put("maxTokens", 500);
        requestData.put("temperature", 0.5);
        requestData.put("repeatPenalty", 5.0);
        requestData.put("stopBefore", new String[] {"###"});
        requestData.put("includeAiFilters", true);
        requestData.put("seed", 0);

        log.info("request data = {}", requestData.toJSONString());

        return requestData;
    }
}
