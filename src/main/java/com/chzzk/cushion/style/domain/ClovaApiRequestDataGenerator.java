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
        String promptSystemMessage = "- 당신의 역할은 사용자와 상대방의 관계를 바탕으로 입력된 대화 내용의 맥락을 파악하고, 이를 통해 상대방의 본래 성격을 분석하는 것입니다.\n" +
                "\n" +
                "사용자 이름: 한재민\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 취준생\n" +
                "상대방 이름: 최근상\n" +
                "상대방 관계: 동료\n" +
                "대화내용: 최근상\n" +
                "재민님, 늦은 시간에 이런 소란을 일으켜서\n" +
                "정말 죄송합니다. 수연님과 저의 개인적인\n" +
                "일로 인해 팀 분위기가 많이 흐려지고, 업무\n" +
                "적인 부분에서도 저로 인해 딜레이가 발생한\n" +
                "점 진심으로 죄송합니다. 더 이상 제가 2팀으\n" +
                "로 가거나 3팀의 디자이너로 남기에는 제그\n" +
                "릇이 작아 더 이상 버티기가 힘들 것 같습니\n" +
                "다. 정말로 죄송합니다.그동안 짧은 시간이\n" +
                "었지만, 재민님과 많은 이야기를 나눌 수있\n" +
                "어 즐거웠고, 재민님께서 원하시는 회사로\n" +
                "꼭취업하시길 기원드립니다. 다시 한번죄\n" +
                "송합니다.\n" +
                "오전 12:49\n" +
                "우선 근상님께서 감정 상할 결정에 내린 것에 대해 진\n" +
                "심으로 죄송합니다.\n" +
                "아까 말씀드렸듯 근상님의 문제로 이러한 결정을 내\n" +
                "린 것이 아니라는 점 너그럽게 이해해주시면 감사하\n" +
                "겠습니다.\n" +
                "저도 근상님과 함께 짧은 시간동안 프로젝트를 수행\n" +
                "하고 이야기를 나누면서 너무나 많은 것들을 배울 수\n" +
                "있었어요.\n" +
                "저는 근상님께서 스터디까지 나가시지 않았으면 하지\n" +
                "만, 온전히 근상님의 결정이기에 아쉬울 따름이네요.\n" +
                "언젠가 좋은 기회로 다시 한 번 만나뵈었으면 좋겠습\n" +
                "니다. 저도 다시 한 번 죄송합니다.\n" +
                "오전 12:53\n" +
                "최근상\n" +
                "전혀 죄송하실 필요 없습니다. 오히려 저희\n" +
                "둘의 개인적인 일로 인해 불편을 끼쳐드린 것\n" +
                "같아 죄송한 마음입니다. 저 역시 스터디에\n" +
                "계속 참여하고 싶지만, 들어올 때마다 오늘\n" +
                "같은 일이 떠오를 것 같아 심적으로 부담이\n" +
                "될꺼같아서 그래서 어쩔수 없이 이런 결정을\n" +
                "내린 점 양해 부탁드립니다. 언젠가 다른 형\n" +
                "태로 다시 만나게 된다면 그때는 웃으며 만나\n" +
                "기를 바랍니다. 정말 감사합니다.\n" +
                "오전 1:02\n" +
                "1\n" +
                "상대방 성격: 신중하고 차분한 성격으로 보입니다. 자신의 행동이 주변에 영향을 미치는 것을 인식하고 있으며, 이에 대해 책임감을 느끼고 있습니다. 또한, 대인관계를 중요시하며 상대방을 배려하는 모습을 보입니다.\n" +
                "###\n" +
                "사용자 이름: 주경민\n" +
                "사용자 소속: 은행\n" +
                "사용자 직무: 대리\n" +
                "상대방 이름: 이은진\n" +
                "상대방 관계: 배우자\n" +
                "대화 내용: 이은진\n" +
                "오빠 오늘 언제와?\n" +
                "몰라 일 마쳐봐야 알 것 같은디\n" +
                "올 때 수영이 픽업좀해와\n" +
                "오늘 학원 어디갔는데?\n" +
                "피아노~\n" +
                "엉 알겠어 몇시까지\n" +
                "9시까지 그 타이어가게 모퉁이에 차세우고 기다리고 있어 시간 괜찮지?\n" +
                "엉 넉넉해 알겠어\n" +
                "고마워~~~\n" +
                "상대방 성격: 가족을 잘 챙기며, 남편의 일정을 고려하여 미리 계획을 세우고 부탁하는 모습을 보임. 또한, 상황 대처 능력이 뛰어나며, 계획적으로 생활하는 것으로 보임\n" +
                "###\n" +
                "사용자 이름: 한재모\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 취준생\n" +
                "상대방 이름: 조아연\n" +
                "상대방 관계: 친구\n" +
                "대화 내용: 12:27\n" +
                "< 한재모\n" +
                "jmo_om9\n" +
                "? 그럼 안되자나요.\n" +
                "한재모 빨리 카카오 들어가서\n" +
                "카맵부터 고쳐조\n" +
                "도레미 전용기를 구매하는게\n" +
                "빠를거같은데?\n" +
                "어어 사주면\n" +
                "타고다님  히\n" +
                "예?\n" +
                "이러면\n" +
                "카카오 가는게 빠를지도\n" +
                "그거다\n" +
                "바로 그마음가짐니다.\n" +
                "토요일에 읽음\n" +
                "메시지 보내기..\n" +
                "상대방 성격: 유머러스하고 긍정적인 성격으로 보입니다. 가벼운 농담을 주고받으며 즐거운 대화를 이끌어내는 스타일이며, 친구의 고민에 대해 가볍게 받아넘기며 응원하는 모습을 보입니다.\n" +
                "### ";

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
