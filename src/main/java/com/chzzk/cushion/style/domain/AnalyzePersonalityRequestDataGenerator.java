package com.chzzk.cushion.style.domain;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class AnalyzePersonalityRequestDataGenerator {

    public JSONObject generateWithConversation(Member member, String conversation, ChatRoom chatRoom) {
        String promptSystemMessage = "- 당신의 역할은 사용자와 상대방의 관계를 바탕으로 입력된 대화 내용의 맥락을 파악하고, 이를 통해 상대방의 본래 성격을 분석하는 것입니다.\n" +
                "- 반드시 답변 앞에 '성격: '을 붙여주세요.\n" +
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
                "성격: 신중하고 차분한 성격으로 보입니다. 자신의 행동이 주변에 영향을 미치는 것을 인식하고 있으며, 이에 대해 책임감을 느끼고 있습니다. 또한, 대인관계를 중요시하며 상대방을 배려하는 모습을 보입니다.\n" +
                "###\n" +
                "사용자 이름: 김민주\n" +
                "사용자 소속: IT회사\n" +
                "사용자 직무: PM\n" +
                "상대방 이름: 한재민\n" +
                "상대방 관계: 동료\n" +
                "대화 내용: 48:21 재민님 솔직히 백엔드 힘든거 알지만\n" +
                "오전 12:31\n" +
                "지금 프론트나 디자인L도 공수가 많아요\n" +
                "오전 12:32\n" +
                "한재민\n" +
                "아니 저도 저희 팀이 맡으면 정말 좋겠죠\n" +
                "오전 12:32\n" +
                "기술적인 보완은 고도화 단계에서 진행하는게 어떤\n" +
                "가요?\n" +
                "오전 12:32\n" +
                "그쵸 백엔드도 지금 안되는 거 하고 있는거 알고있습니\n" +
                "다.\n" +
                "오전 12:33\n" +
                "한재민\n" +
                "근데 지금 해야 하는 일도 있고,, 이게 언제 끝날지\n" +
                "모르겠어서 확답을 드릴 수가 없어요\n" +
                "오전 12:33\n" +
                "그래도 시간상 일정 견적이라도 내어주셔야\n" +
                "제입장에서는 프로젝트를 운영해요\n" +
                "부탁드립니다.\n" +
                "오전 12:33\n" +
                "한재민\n" +
                "HTTPS 연결 작업도 사실 언제 끝날지 미지수고\n" +
                "OCR을 저희가 한다 칩시다\n" +
                "오전 12:33\n" +
                "한재민\n" +
                "그러면 QA를 못할 수도 있는데\n" +
                "+ \n" +
                "성격: 적극적이고 목표 지향적인 성격으로 보입니다. 현재 상황을 빠르게 파악하고, 문제를 해결하기 위해 노력하며, 팀원들과의 협력을 중요시 합니다. 또한, 업무에 대한 책임감이 강하고, 계획적으로 일을 처리하는 것으로 보입니다.\n" +
                "###\n" +
                "사용자 이름: 박진경\n" +
                "사용자 소속: IT 중견기업\n" +
                "사용자 직무: 마케팅\n" +
                "상대방 이름: 김은진\n" +
                "상대방 관계: 상사\n" +
                "대화 내용: 7:29 박대리 아까 보낸 자료 왜이래?\n" +
                "김은진\n" +
                "목차도 엉망이고 이거 제대로 보낸거 맞아?\n" +
                "오후 12:53\n" +
                "헉 죄송합니다.. 수정 전 자료가 간 것 같아요..\n" +
                "금방 다시 보내겠습니다..!\n" +
                "오후 12:53 \n" +
                "김은진 똑바로 체크하라고 내가 몇번을 말했어?\n" +
                "제대로 하는 것도 없고, 맨날 입 한번 더 대야하고\n" +
                "오후 12:53\n" +
                "죄송합니다.. 다음부터 이런 일 없도록 하겠습니다.\n" +
                "오후 12:53\n" +
                "김은진\n" +
                "그 말도 진짜 도대체 몇번이야. 정신 좀 챙기고 일합시다 좀.\n" +
                "오후 12:53\n" +
                "넵 자료 지금 다시 보내드리겠습니다.\n" +
                "+ #\n" +
                "성격: 꼼꼼하고 엄격한 성격으로 보입니다. 일 처리에 있어서 정확성을 중요시하며, 실수에 대해 용납하지 않는 모습을 보입니다. 또한, 부하 직원에게 업무 지시를 내릴 때 명확하게 하며, 피드백을 줄 때는 직설적으로 하는 편입니다.\n" +
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
                "성격 : 자상하고 배려심이 깊은 성격으로 보입니다. 가족 구성원의 일을 신경쓰고 도우며, 특히 아내의 부탁을 잘 들어주는 것으로 보입니다. 일과 가정 생활 모두 균형있게 유지하려고 노력하는 것 같습니다.";

        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", promptSystemMessage);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", createUserMessageForAnalyzingPersonality(member, chatRoom, conversation));

        return getRequestData(system, user);
    }

    private String createUserMessageForAnalyzingPersonality(Member member, ChatRoom chatRoom, String userMessage) {
        StringBuffer sb = new StringBuffer();
        sb.append("사용자 이름: ").append(member.getRealName()).append("\n");
        sb.append("사용자 소속: ").append(member.getAffiliation()).append("\n");
        sb.append("사용자 직무: ").append(member.getJob()).append("\n");
        sb.append("상대방 이름: ").append(chatRoom.getPartnerName()).append("\n");
        sb.append("상대방 관계: ").append(chatRoom.getPartnerRel().getLabel()).append("\n");
        sb.append("대화 내용: ").append(userMessage).append("\n");
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
