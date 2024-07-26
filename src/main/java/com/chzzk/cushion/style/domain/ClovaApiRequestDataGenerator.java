package com.chzzk.cushion.style.domain;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ClovaApiRequestDataGenerator {

    public JSONObject generateWithUserMessage(Member member, String sentence, ChatRoom chatRoom) {
        String promptSystemMessage = "ㅎㅇ";

        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", promptSystemMessage);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", createPromptUserMessage(member, chatRoom, sentence, "문장: "));

        return getRequestData(system, user);
    }

    public JSONObject generateWithConversation(Member member, String conversation, ChatRoom chatRoom) {
        String promptSystemMessage = "ㅎㅇ";

        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", promptSystemMessage);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", createPromptUserMessage(member, chatRoom, conversation, "대화 내용: "));

        return getRequestData(system, user);
    }

    private String createPromptUserMessage(Member member, ChatRoom chatRoom, String userMessage, String sysTopic) {
        StringBuffer sb = new StringBuffer();
        sb.append("사용자 이름: ").append(member.getRealName()).append("\n");
        sb.append("사용자 소속: ").append(member.getAffiliation()).append("\n");
        sb.append("사용자 직무: ").append(member.getJob()).append("\n");
        sb.append("상대방 이름: ").append(chatRoom.getPartnerName()).append("\n");
        sb.append("상대방 관계: ").append(chatRoom.getPartnerRel().getLabel()).append("\n");
        sb.append(sysTopic).append(userMessage).append("\n");
        return sb.toString();
    }

    private JSONObject getRequestData(JSONObject system, JSONObject user) {
        JSONArray presetText = new JSONArray();
        presetText.add(system);
        presetText.add(user);

        JSONObject requestData = new JSONObject();
        requestData.put("messages", presetText);
        requestData.put("topP", 0.8);
        requestData.put("topK", 0);
        requestData.put("maxTokens", 500);
        requestData.put("temperature", 0.5);
        requestData.put("repeatPenalty", 5.0);
        requestData.put("stopBefore", new JSONArray());
        requestData.put("includeAiFilters", true);
        requestData.put("seed", 0);
        return requestData;
    }
}
