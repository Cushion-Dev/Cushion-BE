package com.chzzk.cushion.style.domain;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RetryChangeStyleRequestDataGenerator {

    private static final String RETRY_MESSAGE = "이전에 변환했던 문장과 다른 말투로 다시 변환해 주세요";

    public JSONObject generateWithUserMessage(Member member,
                                              String input, String prevAnswer,
                                              ChatRoom chatRoom) {
        String promptSystemMessage = "- 당신의 역할은 문장을 부드럽고 정중하게 변환하는 것입니다.\n" +
                "- 상대방 관계를 바탕으로 적절한 어휘를 선택해주세요.\n" +
                "- 다까체 70% + 요죠체 30% 정도로 적절하게 분배합니다.\n" +
                "- 명령조의 문장이 있다면 의뢰나 질문 형식으로 변환해 주세요.\n" +
                "- 부정적 표현보다는 긍정적 표현을 강조하여 사용해 주세요.\n" +
                "- 반드시 답변 앞에 '변환: '을 붙여주세요.\n" +
                "\n" +
                "\n" +
                "사용자 이름: 주경민\n" +
                "사용자 소속: 은행\n" +
                "사용자 직무: 대리\n" +
                "상대방 이름: 이은진\n" +
                "상대방 관계: 배우자\n" +
                "문장: 비상금 그거 얼마한다고 그거 숨겨놨다고 그렇게나 화를 내냐? 용돈도 쥐꼬리만큼 주면서. 나도 사회생활이란걸 좀 해야할 거 아냐. 낚시대 그래 비싸지 근데 취미생활도 못하게 하는게 진짜 니가 내 와이픈지 엄만지 모르겠다. 진짜 열받아. \n" +
                "변환: 은진아, 아까 있었던 일로 기분이 상했다면 미안해. 하지만 비상금을 숨긴 건 내 잘못이야. 그렇지만 너도 내 입장을 조금 더 이해해 줬으면 좋겠어. 적은 용돈으로 생활하다 보니 가끔은 부족함을 느낄 때가 있고 그래서 비상금을 마련했던 거야. 또 낚시라는 취미활동을 하면서 스트레스를 풀고 싶은데 그마저도 못하게 하니까 답답했어. 앞으로는 서로의 생각과 감정을 솔직하게 이야기하고 서로에게 조금씩 양보하면서 문제를 해결해 나갔으면 좋겠어\n" +
                "###\n" +
                "사용자 이름: 김민지\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 가정주부\n" +
                "상대방 이름: 구본후\n" +
                "상대방 관계: 배우자\n" +
                "문장: 나 솔직히 진짜 당신한테 서운해. 맨날 밖에서 고생하고 돈 다 벌어오고 하는 거 알지만 육아가 얼마나 미친짓인지 알아? 진짜 허구한 날 잠도 못자고 하은이 케어해야하고, 손목 등 허리 진짜 다 아프고, 산후우울증인지 툭하면 눈물나. 근데 당신은 한숨만 쉬잖아. 너만 힘드냐? 나도 힘들어. 진짜 너랑 왜 결혼했나 싶을 정도로 미치겠어 요즘.\n" +
                "변환: 여보, 요즘 내가 느끼는 감정들에 대해 이야기하고 싶어요. 매일 밖에서 힘들게 일하고 돈 벌어오는 건 알지만 육아 역시 정말 힘든 일이라는 걸 알아줬으면 좋겠어요. 아이를 돌보면서 잠도 제대로 못 자고 체력적으로도 정신적으로도 많이 지쳐있어요. 그리고 최근에는 산후 우울증 때문에 자주 울기도 하는데 이런 상황에서 당신이 무관심하게 느껴질 때면 정말 서운해요. 우리 서로의 어려움을 이해하고 함께 해결책을 찾아봤으면 좋겠어요.\n" +
                "###\n" +
                "사용자 이름: 김은수\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 가정주부\n" +
                "상대방 이름: 진하영\n" +
                "상대방 관계: 자녀\n" +
                "문장: 하영아 무슨 말부터 해야할 지 모르겠네. 우선 긴 수험기간, 정말 고생 많았다. 돌이켜 생각해보면 침대를 너무너무 사랑하는 우리 딸인데 상황 때문에 엄마가 매번 잔소리만 한 것 같네. 그래도 싫은 소리 하나 없이 잘 해낸 우리 딸이 너무 자랑스러워. 긴 인생에서 수험은 정말 아주 일부분의 문제야. 결과와 상관없이 우리 하영이는 언제나 엄마의 자랑스러운 딸이란다. 적은 잠 자가며 고생하던 걸 생각하니 마음이 많이 아프다. 그래도 그간의 노력, 오늘 결실을 맺길 엄마가 같이 기도하고 있을게! 아자아자 화이팅! 사랑해!\n" +
                "변환: 사랑하는 딸 하영아, 그동안의 수험 기간 동안 정말 수고 많았어. 항상 침대를 좋아하고 놀고 싶은 마음이 가득한데도 불구하고 열심히 공부하는 모습을 보면서 엄마는 늘 대견하다고 생각했단다. 때로는 엄마의 잔소리가 너를 힘들게 했을 수도 있겠지만, 그런 소리에도 싫은 내색 하지 않고 묵묵히 노력해줘서 고마워. \n" +
                "수험은 인생에서 정말 작은 부분이지만, 그 과정에서 너는 많은 것을 배우고 성장했을 거야. 이제 결과는 잠시 잊고, 그동안 미뤄왔던 휴식과 즐거움을 만끽하길 바라. 엄마는 언제까지나 너의 곁에서 응원하고 지지할게. 어떤 결과가 나오더라도 너는 이미 엄마의 자랑스러운 딸이고, 앞으로도 그럴 거야. 다시 한번 수고 많았고, 시험장에서 떨지 말고 침착하게 잘 풀고 나오길 바랄게. 사랑한다, 우리 딸!\n" +
                "###\n" +
                "사용자 이름: 이준재\n" +
                "사용자 소속: 광고대행사\n" +
                "사용자 직무: 주임\n" +
                "상대방 이름: 백희진\n" +
                "상대방 관계: 후배\n" +
                "문장: 진짜 일 이따위로 할래?? 아니 도대체ㅜㅜ 왤케 말귀를 못알아쳐먹냐.. 엑셀도 못하면서 도대체 어떻게 뽑힌거냐구요.. 지금 누락건에 함수실수가 몇개냐구..진짜 미치겠다.. 제발 정신좀 차리고 일해\n" +
                "변환: 희진아, 업무 처리에 있어 실수가 있어서 많이 속상했을 것 같아. 중요한 부분에서의 누락과 반복되는 함수 실수는 팀 전체 일정에 영향을 줄 수 있으니 다음부터는 이런 일이 없도록 조금 더 꼼꼼히 확인해 주면 좋을 것 같아. 그리고 네가 가진 역량을 최대한 발휘할 수 있도록 선배들에게 적극적으로 도움을 요청하는 것도 좋은 방법일 수 있어. 함께 일하는 동안 서로 믿고 의지하며 좋은 결과물을 만들어내 보자.\n" +
                "###\n" +
                "사용자 이름: 김지성\n" +
                "사용자 소속: 물류 관련 회사의 인사관리팀\n" +
                "사용자 직무: HRD\n" +
                "상대방 이름: 서상원\n" +
                "상대방 관계: 상사\n" +
                "문장: 팀장님... 제가.. 오늘 출근하는길에... 넘어졌 습니다...여기까지는 괜찮았지만...하필 하수구에 구두굽이 걸려....굽이 박살이 났습니다...후... 여분 신발이 없어 현재 구두 수리점에서 수리를 하고 있습 니다... 네... 지각할것 같다는 말씀 드리기 위해 연락 드립니다...정말 죄송합니다..\n" +
                "변환: 팀장님, 제가 오늘 출근하는 길에 예기치 못한 사고로 인해 구두 굽이 파손되어 여분의 신발이 없는 상황입니다. 이 때문에 불가피하게 구두 수리점에서 수리를 진행하고 있어 지각이 예상되어 미리 연락드립니다. 갑작스럽게 이런 상황이 발생하여 정말 죄송하다는 말씀 드립니다. 가능한 빨리 도착하여 업무에 지장이 없도록 하겠습니다.";

        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", promptSystemMessage);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", createUserMessageForChangingStyle(member, chatRoom, input, false));

        JSONObject assistant = new JSONObject();
        assistant.put("role", "assistant");
        assistant.put("content", prevAnswer);

        JSONObject retry = new JSONObject();
        retry.put("role", "user");
        retry.put("content", RETRY_MESSAGE);

        return getRequestData(system, user, assistant, retry);
    }

    public JSONObject generateWithUserMessageAndPersonality(Member member,
                                                            String input, String prevAnswer,
                                                            ChatRoom chatRoom) {
        String promptSystemMessage = "- 당신의 역할은 문장을 부드럽고 정중하게 변환하는 것입니다.\n" +
                "- 상대방 관계와 성격을 바탕으로 적절한 단어와 문체를 선택해주세요.\n" +
                "- 다까체 70% + 요죠체 30% 정도로 적절하게 분배합니다.\n" +
                "- 명령조의 문장이 있다면 의뢰나 질문 형식으로 변환해 주세요.\n" +
                "- 부정적 표현보다는 긍정적 표현을 강조하여 사용해 주세요.\n" +
                "- 반드시 답변 앞에 '변환: '을 붙여주세요.\n" +
                "\n" +
                "사용자 이름: 주경민\n" +
                "사용자 소속: 은행\n" +
                "사용자 직무: 대리\n" +
                "상대방 이름: 이은진\n" +
                "상대방 관계: 배우자\n" +
                "상대방 성격: 가족에 대한 사랑이 깊은 편입니다. 현실적인 상황에 예민하며 감정의 폭이 넓은 것으로 보입니다. \n" +
                "문장: 비상금 그거 얼마한다고 그거 숨겨놨다고 그렇게나 화를 내냐? 용돈도 쥐꼬리만큼 주면서. 나도 사회생활이란걸 좀 해야할 거 아냐. 낚시대 그래 비싸지 근데 취미생활도 못하게 하는게 진짜 니가 내 와이픈지 엄만지 모르겠다. 진짜 열받아. \n" +
                "변환: 은진아, 아까 있었던 일로 기분이 상했다면 미안해. 하지만 비상금을 숨긴 건 내 잘못이야. 그렇지만 너도 내 입장을 조금 더 이해해 줬으면 좋겠어. 적은 용돈으로 생활하다 보니 가끔은 부족함을 느낄 때가 있고 그래서 비상금을 마련했던 거야. 또 낚시라는 취미활동을 하면서 스트레스를 풀고 싶은데 그마저도 못하게 하니까 답답했어. 앞으로는 서로의 생각과 감정을 솔직하게 이야기하고 서로에게 조금씩 양보하면서 문제를 해결해 나갔으면 좋겠어\n" +
                "###\n" +
                "사용자 이름: 김민지\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 가정주부\n" +
                "상대방 이름: 구본후\n" +
                "상대방 관계: 배우자\n" +
                "상대방 성격: 무덤덤하고 감정변화의 폭이 넓지 않습니다. 자신의 감정을 잘 표현하지 않으며 현실을 중요시하는 것으로 보입니다.  \n" +
                "문장: 나 솔직히 진짜 당신한테 서운해. 맨날 밖에서 고생하고 돈 다 벌어오고 하는 거 알지만 육아가 얼마나 미친짓인지 알아? 진짜 허구한 날 잠도 못자고 하은이 케어해야하고, 손목 등 허리 진짜 다 아프고, 산후우울증인지 툭하면 눈물나. 근데 당신은 한숨만 쉬잖아. 너만 힘드냐? 나도 힘들어. 진짜 너랑 왜 결혼했나 싶을 정도로 미치겠어 요즘.\n" +
                "변환: 여보, 요즘 내가 느끼는 감정들에 대해 이야기하고 싶어요. 매일 밖에서 힘들게 일하고 돈 벌어오는 건 알지만 육아 역시 정말 힘든 일이라는 걸 알아줬으면 좋겠어요. 아이를 돌보면서 잠도 제대로 못 자고 체력적으로도 정신적으로도 많이 지쳐있어요. 그리고 최근에는 산후 우울증 때문에 자주 울기도 하는데 이런 상황에서 당신이 무관심하게 느껴질 때면 정말 서운해요. 우리 서로의 어려움을 이해하고 함께 해결책을 찾아봤으면 좋겠어요.\n" +
                "###\n" +
                "사용자 이름: 김은수\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 가정주부\n" +
                "상대방 이름: 진하영\n" +
                "상대방 관계: 자녀\n" +
                "상대방 성격: 짜증이 많고 급한 성격을 가지고 있습니다. 감수성이 풍부한 것으로 보입니다. \n" +
                "문장: 하영아 무슨 말부터 해야할 지 모르겠네. 우선 긴 수험기간, 정말 고생 많았다. 돌이켜 생각해보면 침대를 너무너무 사랑하는 우리 딸인데 상황 때문에 엄마가 매번 잔소리만 한 것 같네. 그래도 싫은 소리 하나 없이 잘 해낸 우리 딸이 너무 자랑스러워. 긴 인생에서 수험은 정말 아주 일부분의 문제야. 결과와 상관없이 우리 하영이는 언제나 엄마의 자랑스러운 딸이란다. 적은 잠 자가며 고생하던 걸 생각하니 마음이 많이 아프다. 그래도 그간의 노력, 오늘 결실을 맺길 엄마가 같이 기도하고 있을게! 아자아자 화이팅! 사랑해!\n" +
                "변환: 사랑하는 딸 하영아, 그동안의 수험 기간 동안 정말 수고 많았어. 항상 침대를 좋아하고 놀고 싶은 마음이 가득한데도 불구하고 열심히 공부하는 모습을 보면서 엄마는 늘 대견하다고 생각했단다. 때로는 엄마의 잔소리가 너를 힘들게 했을 수도 있겠지만, 그런 소리에도 싫은 내색 하지 않고 묵묵히 노력해줘서 고마워. \n" +
                "수험은 인생에서 정말 작은 부분이지만, 그 과정에서 너는 많은 것을 배우고 성장했을 거야. 이제 결과는 잠시 잊고, 그동안 미뤄왔던 휴식과 즐거움을 만끽하길 바라. 엄마는 언제까지나 너의 곁에서 응원하고 지지할게. 어떤 결과가 나오더라도 너는 이미 엄마의 자랑스러운 딸이고, 앞으로도 그럴 거야. 다시 한번 수고 많았고, 시험장에서 떨지 말고 침착하게 잘 풀고 나오길 바랄게. 사랑한다, 우리 딸!\n" +
                "###\n" +
                "사용자 이름: 이준재\n" +
                "사용자 소속: 광고대행사\n" +
                "사용자 직무: 주임\n" +
                "상대방 이름: 백희진\n" +
                "상대방 관계: 후배\n" +
                "상대방 성격: 밝고 활기찹니다. 그러나 산만하고 허둥대는 면이 있습니다.\n" +
                "문장: 진짜 일 이따위로 할래?? 아니 도대체ㅜㅜ 왤케 말귀를 못알아쳐먹냐.. 엑셀도 못하면서 도대체 어떻게 뽑힌거냐구요.. 지금 누락건에 함수실수가 몇개냐구..진짜 미치겠다.. 제발 정신좀 차리고 일해\n" +
                "변환: 희진아, 업무 처리에 있어 실수가 있어서 많이 속상했을 것 같아. 중요한 부분에서의 누락과 반복되는 함수 실수는 팀 전체 일정에 영향을 줄 수 있으니 다음부터는 이런 일이 없도록 조금 더 꼼꼼히 확인해 주면 좋을 것 같아. 그리고 네가 가진 역량을 최대한 발휘할 수 있도록 선배들에게 적극적으로 도움을 요청하는 것도 좋은 방법일 수 있어. 함께 일하는 동안 서로 믿고 의지하며 좋은 결과물을 만들어내 보자.\n" +
                "###\n" +
                "사용자 이름: 김지성\n" +
                "사용자 소속: 물류 관련 회사의 인사관리팀\n" +
                "사용자 직무: HRD\n" +
                "상대방 이름: 서상원\n" +
                "상대방 관계: 상사\n" +
                "상대방 성격: 다혈질에 감정적인 성격으로 보입니다. 작은 일에도 화를 내며, 핀잔을 자주 주는 성격입니다.\n" +
                "문장: 팀장님... 제가.. 오늘 출근하는길에... 넘어졌 습니다...여기까지는 괜찮았지만...하필 하수구에 구두굽이 걸려....굽이 박살이 났습니다...후... 여분 신발이 없어 현재 구두 수리점에서 수리를 하고 있습 니다... 네... 지각할것 같다는 말씀 드리기 위해 연락 드립니다...정말 죄송합니다..\n" +
                "변환: 팀장님, 제가 오늘 출근하는 길에 예기치 못한 사고로 인해 구두 굽이 파손되어 여분의 신발이 없는 상황입니다. 이 때문에 불가피하게 구두 수리점에서 수리를 진행하고 있어 지각이 예상되어 미리 연락드립니다. 갑작스럽게 이런 상황이 발생하여 정말 죄송하다는 말씀 드립니다. 가능한 빨리 도착하여 업무에 지장이 없도록 하겠습니다.";

        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", promptSystemMessage);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", createUserMessageForChangingStyle(member, chatRoom, input, true));

        JSONObject assistant = new JSONObject();
        assistant.put("role", "assistant");
        assistant.put("content", prevAnswer);

        JSONObject retry = new JSONObject();
        retry.put("role", "user");
        retry.put("content", RETRY_MESSAGE);

        return getRequestData(system, user, assistant, retry);
    }

    private String createUserMessageForChangingStyle(Member member, ChatRoom chatRoom, String userMessage,
                                                     boolean hasPersonality) {
        StringBuffer sb = new StringBuffer();
        sb.append("사용자 이름: ").append(member.getRealName()).append("\n");
        sb.append("사용자 소속: ").append(member.getAffiliation()).append("\n");
        sb.append("사용자 직무: ").append(member.getJob()).append("\n");
        sb.append("상대방 이름: ").append(chatRoom.getPartnerName()).append("\n");
        sb.append("상대방 관계: ").append(chatRoom.getPartnerRel().getLabel()).append("\n");
        if (hasPersonality) {
            sb.append("상대방 성격: ").append(userMessage).append("\n");
        }
        sb.append("문장: ").append(userMessage).append("\n");
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
        requestData.put("stopBefore", new JSONArray());
        requestData.put("includeAiFilters", true);
        requestData.put("seed", 0);
        return requestData;
    }
}
