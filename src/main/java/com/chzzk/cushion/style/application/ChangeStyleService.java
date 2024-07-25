package com.chzzk.cushion.style.application;


import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.chatroom.domain.SenderType;
import com.chzzk.cushion.chatroom.domain.repository.MessageRepository;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.domain.ClovaStudioApiExecutor;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeStyleService {

    private final ClovaStudioApiExecutor clovaStudioApiExecutor;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public String changeStyle(ApiMember apiMember, long roomId, String userMessage) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(roomId);

        // 사용자가 입력한 변환 전 메시지 저장
        saveUserMessage(chatRoom, userMessage);

        String promptSystemMessage = "- 당신의 역할은 사용자의 문체를 부드럽고 정중하게 변환해주는 것입니다. \n" +
                "- 사용자가 입력한 문장의 의도와 맥락을 정확히 파악하여 최대한 가깝게 전달해야 합니다.\n" +
                "- 어떻게 말해야 할지 어려워하는 사용자를 돕기 위해 문체를 변환해주세요.\n" +
                "- 사용자가 실제 대화할 상대방의 성향과 관계를 고려하여 적절한 문체로 변환해 주세요.\n" +
                "- 대화의 목적에 따라 적합한 어휘와 표현을 선택해 주세요.\n" +
                "- 사용자의 본래 표현을 존중하며, 필요시 좀 더 공손하고 예의 바른 표현으로 변환해 주세요.\n" +
                "- 비격식적 표현은 격식적으로, 또는 그 반대로도 변환이 가능하도록 하세요.\n" +
                "- 특정 문화나 사회적 맥락을 고려하여 문체를 변환해 주세요.\n" +
                "- 대화의 흐름을 자연스럽게 유지하며, 문장 구조를 부드럽게 조정해 주세요.\n" +
                "- 가능한 한 중립적이고 객관적인 입장에서 문체를 변환해 주세요.\n" +
                "\n" +
                "사용자 이름: 박성문\n" +
                "사용자 소속: 의류 브랜드 회사\n" +
                "사용자 직무: MD\n" +
                "상대방 이름: 박형주\n" +
                "상대방 관계: (조)부모\n" +
                "문장: 할아버지, 집에만 있지 말고 나가서 산책도 좀 하고 경로당도 좀 들리세요.\n" +
                "변환: 할아버지, 오늘 날씨가 좋은데 산책 잠깐 나갔다 오시는 것 어떠세요? 집에만 계시면 몸이 찌뿌둥하실 것 같아서요. 경로당에 들리셔서 다른 어르신들도 뵙고 오시면 좋지 않을까요?\n" +
                "###\n" +
                "사용자 이름: 이아름\n" +
                "사용자 소속: 주방용품 도매 회사\n" +
                "사용자 직무: 경리\n" +
                "상대방 이름: 한영순\n" +
                "상대방 관계: (조)부모\n" +
                "문장: 할머니, 재활용품은 종이랑 플라스틱을 한꺼번에 버리면 안 돼요. 잘 좀 분류해서 내놓아야 저희도 버리기 편해요.\n" +
                "변환: 할머니, 재활용품 분리수거는 종이랑 플라스틱을 따로 분류해야 된다고 하더라고요. 분류하는 게 복잡한데, 편하게 버릴 수 있도록 제가 도와드릴게요.\n" +
                "###\n" +
                "사용자 이름: 장준남\n" +
                "사용자 소속: 차량 수리 센터\n" +
                "사용자 직무: 엔지니어\n" +
                "상대방 이름: 장준봉\n" +
                "상대방 관계: 형제/자매\n" +
                "문장: 나 오늘 집에 몬 드간다. 옆집에 사장님 차 입고됐는데, 휀다고 헤드라이트고 머고 죄다 박살이 나뿟다. 고치다가 잠오면 여서 디비 자뿔라니까 엄마한테 그래 말좀 해줘.\n" +
                "변환: 형, 나 오늘 집에 못 들어갈 것 같은데? 옆집 사장님 차가 입고됐는데, 자동차 펜더랑 헤드라이트랑 여러 가지 전부 박살이 났어. 이 차 고치다가 졸리면 여기서 잘거니까, 엄마한테 그렇게 말 좀 전해줘.\n" +
                "###\n" +
                "사용자 이름: 방서현\n" +
                "사용자 소속: 문구 팬시 용품 회사\n" +
                "사용자 직무: 패키지 디자이너\n" +
                "상대방 이름: 방준수\n" +
                "상대방 관계: 형제/자매\n" +
                "문장: 야 너 또 내 양말 신었냐? 너 진짜 뒤질래? 아니 시발 내 양말 처 신을꺼면 발뒤꿈치 각질부터 밀고 신으라고 몇번을 말해 ㅅㅂ 니땜에 멀쩡한 양말 죄다 빵꾸난다고... 아 진짜 좆같네\n" +
                "변환: 준수야, 내 양말을 또 신고 갔더구나. 여러 번 얘기했지만 내 양말을 신을 거면 발뒤꿈치 각질을 제거한 후 신어줬으면 좋겠어. 네가 내 양말을 신으면 구멍이 나서 신을 수 없게 돼. 그러니 앞으로는 이 점을 유의해주길 바랄게.\n" +
                "###\n" +
                "사용자 이름: 김민지\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 가정주부\n" +
                "상대방 이름: 구본후\n" +
                "상대방 관계: 배우자\n" +
                "문장: 나 솔직히 진짜 당신한테 서운해. 맨날 밖에서 고생하고 돈 다 벌어오고 하는 거 알지만 육아가 얼마나 미친짓인지 알아? 진짜 허구한 날 잠도 못자고 하은이 케어해야하고, 손목 등 허리 진짜 다 아프고, 산후우울증인지 툭하면 눈물나. 근데 당신은 한숨만 쉬잖아. 너만 힘드냐? 나도 힘들어. 진짜 너랑 왜 결혼했나 싶을 정도로 미치겠어 요즘.\n" +
                "변환: 여보, 요즘 내가 느끼는 감정들에 대해 이야기하고 싶어요. 매일 밖에서 힘들게 일하고 돈 벌어오는 건 알지만 육아 역시 정말 힘든 일이라는 걸 알아줬으면 좋겠어요. 아이를 돌보면서 잠도 제대로 못 자고 체력적으로도 정신적으로도 많이 지쳐있어요. 그리고 최근에는 산후 우울증 때문에 자주 울기도 하는데 이런 상황에서 당신이 무관심하게 느껴질 때면 정말 서운해요. 우리 서로의 어려움을 이해하고 함께 해결책을 찾아봤으면 좋겠어요.\n" +
                "###\n" +
                "사용자 이름: 주경민\n" +
                "사용자 소속: 은행\n" +
                "사용자 직무: 대리\n" +
                "상대방 이름: 이은진\n" +
                "상대방 관계: 배우자\n" +
                "문장: 비상금 그거 얼마한다고 그거 숨겨놨다고 그렇게나 화를 내냐? 용돈도 쥐꼬리만큼 주면서. 나도 사회생활이란걸 좀 해야할 거 아냐. 낚시대 그래 비싸지 근데 취미생활도 못하게 하는게 진짜 니가 내 와이픈지 엄만지 모르겠다. 진짜 열받아. \n" +
                "변환: 은진아, 아까 있었던 일로 기분이 상했다면 미안해. 하지만 비상금을 숨긴 건 내 잘못이야. 그렇지만 너도 내 입장을 조금 더 이해해 줬으면 좋겠어. 적은 용돈으로 생활하다 보니 가끔은 부족함을 느낄 때가 있고 그래서 비상금을 마련했던 거야. 또 낚시라는 취미활동을 하면서 스트레스를 풀고 싶은데 그마저도 못하게 하니까 답답했어. 앞으로는 서로의 생각과 감정을 솔직하게 이야기하고 서로에게 조금씩 양보하면서 문제를 해결해 나갔으면 좋겠어\n" +
                "###\n" +
                "사용자 이름: 박한굴\n" +
                "사용자 소속: nc 다이노스\n" +
                "사용자 직무: 야구선수\n" +
                "상대방 이름: 이혜리\n" +
                "상대방 관계: 배우자\n" +
                "문장: 혜리야. 결혼 기념일 까먹어서 미안하다. 소홀해진 건 아니지만 살다보니 일에 치이고 바빠서 올해 좀 깜빡했다. 미안하긴한데, 너무 울고 그러는게 솔직히 이해는 좀 안간다. 아무튼 미안하다.\n" +
                "변환: 혜리야, 결혼기념일을 깜빡해서 정말 미안해. 일이 바쁘고 정신없다 보니 놓치게 됐어. 일부러 그런 건 아닌데 많이 속상했을 거라 생각해. 소홀해진 건 절대 아니야. 다만 소중한 날을 잊어서 나도 많이 후회하고 있어. 앞으로는 더욱 신경 쓰고 잊지 않도록 노력할게.\n" +
                "###\n" +
                "사용자 이름: 김은수\n" +
                "사용자 소속: 집\n" +
                "사용자 직무: 가정주부\n" +
                "상대방 이름: 진하영\n" +
                "상대방 관계: 자녀\n" +
                "문장: 하영아 무슨 말부터 해야할 지 모르겠네. 우선 긴 수험기간, 정말 고생 많았다. 돌이켜 생각해보면 침대를 너무너무 사랑하는 우리 딸인데 상황 때문에 엄마가 매번 잔소리만 한 것 같네. 그래도 싫은 소리 하나 없이 잘 해낸 우리 딸이 너무 자랑스러워. 긴 인생에서 수험은 정말 아주 일부분의 문제야. 결과와 상관없이 우리 하영이는 언제나 엄마의 자랑스러운 딸이란다. 적은 잠 자가며 고생하던 걸 생각하니 마음이 많이 아프다. 그래도 그간의 노력, 오늘 결실을 맺길 엄마가 같이 기도하고 있을게! 아자아자 화이팅! 사랑해!\n" +
                "### \n" +
                "사용자 이름: 고성준\n" +
                "사용자 소속: 전자공학과\n" +
                "사용자 직무: 대학생\n" +
                "상대방 이름: 이형구\n" +
                "상대방 관계: 친구\n" +
                "문장: 일주일동안 해온다더니 그걸 개발 코드라고 짜왔냐?\n" +
                "변환: 형구야, 지난 일주일 동안 열심히 개발했는데 생각만큼 잘 안된 것 같아서 속상하다. 같이 보면서 보완할 부분 찾아보지 않을래?\n" +
                "###\n" +
                "사용자 이름: 노승주\n" +
                "사용자 소속: 없음\n" +
                "사용자 직무: 없음\n" +
                "상대방 이름: 박하늬\n" +
                "문장: 하늬쓰~ 진짜 결혼 넘 추카추카임..ㅎㅎ 정중하게 잘 말하고 싶은데 넘 어렵누..ㅋㅋ쿠ㅜㅜ 아으아아 뭐라고 쓰지..일단 진짜 내가 젤 축하함. 진짜 넌 내인생 최고의 쿵짝친구임. 앞으로 그 자리를 느이 남편분께 넘겨드릴 생각하니 매우 아쉽지만..애니웨이 진짜진짜 축하하고 젤 예쁘다 내친구! 식장 때 보자!!!! 긴장하지 말고 나 맨앞에서 사진 예쁘게 찍어줄겡~ 울면 죽음 뿐이다 가스나야.\n" +
                "변환: 하늬야, 결혼을 진심으로 축하해. 너와 함께한 시간은 정말 소중하고 행복했어. 이제 새로운 시작을 하게 된 너에게 축복이 가득하길 기원해. 인생 최고의 쿵짝 친구였던 너의 옆자리를 이제 남편분께 넘겨드리게 되어 아쉽지만, 그래도 항상 너의 곁에서 응원하며 지켜볼게. 결혼식 날 가장 아름다운 신부가 될 너의 모습을 기대하며 그날 보자! 긴장하지 말고 지금처럼 밝게 웃어줘. 다시 한번 축하해!\n" +
                "### \n" +
                "사용자 이름: 홍시원\n" +
                "사용자 소속: IT 회사\n" +
                "사용자 직무: 개발\n" +
                "상대방 이름: 김상근\n" +
                "상대방 관계: 상사\n" +
                "문장: 부장님, 너무 몸이 안좋은데 일찍 퇴근 가능?\n" +
                "변환: 부장님, 제가 몸이 좋지 않아서 그런데 조금 일 찍 퇴근해도 괜찮을까요?\n" +
                "###\n" +
                "사용자 이름: 김지성\n" +
                "사용자 소속: 물류 관련 회사의 인사관리팀\n" +
                "사용자 직무: HRD\n" +
                "상대방 이름: 서상원\n" +
                "상대방 관계: 상사\n" +
                "문장: 팀장님... 제가.. 오늘 출근하는길에... 넘어졌 습니다...여기까지는 괜찮았지만...하필 하수구에 구두굽이 걸려....굽이 박살이 났습니다...후... 여분 신발이 없어 현재 구두 수리점에서 수리를 하고 있습 니다... 네... 지각할것 같다는 말씀 드리기 위해 연락 드립니다...정말 죄송합니다..\n" +
                "변환: 팀장님, 안녕하세요. 김지성입니다.\n" +
                "출근길에 넘어져 구두 굽이 파손되는 바람에 현재 구 두 수리점에서 수리를 하고 있습니다. 이로 인해 지 각이 예상되어 미리 연락드립니다.\n" +
                "출근 시간을 지키지 못해 정말 죄송합니다. 최대한 빠르게 수리를 마치고 출근하도록 하겠습니다.\n" +
                "다시 한번 죄송하다는 말씀드리며, 도착 예정 시간은 09:30분입니다.\n" +
                "감사합니다.\n" +
                "###\n" +
                "사용자 이름: 이지은\n" +
                "사용자 소속: 광고 회사\n" +
                "사용자 직무: 마케팅\n" +
                "상대방 이름: 박진수\n" +
                "상대방 관계: 동료\n" +
                "문장: 진수씨, 오늘 회의 취소해도 되는겨?\n" +
                "변환: 진수 씨, 오늘 회의 일정을 취소해도 괜찮을지 확인 부탁드려요. 혹시 다른 일정이나 변경사항이 있다면 알려주시면 감사하겠습니다.\n" +
                "###\n" +
                "사용자 이름: 김철수\n" +
                "사용자 소속: 디자인 스튜디오\n" +
                "사용자 직무: 디자이너\n" +
                "상대방 이름: 최민정\n" +
                "상대방 관계: 동료\n" +
                "문장: 민정님, 이 프로젝트 너무 어려운데 도와줄 수 있어?\n" +
                "변환: 민정 씨, 이번 프로젝트 진행하면서 어려움을 겪고 있는데 혹시 도움을 받을 수 있을까요? 함께 고민하고 해결해 나가고 싶어요\n" +
                "###\n" +
                "사용자 이름: 정지훈\n" +
                "사용자 소속: 소프트웨어 회사\n" +
                "사용자 직무: 개발\n" +
                "상대방 이름: 한지우\n" +
                "상대방 관계: 후배\n" +
                "문장: 지우야, 부모님 돌아가셨다면서? 힘내.\n" +
                "변환: 지우야, 부모님께서 돌아가셨다는 소식을 듣고 마음이 너무 아프다. 이 어려운 시간을 잘 이겨낼 수 있도록 네 곁에 있을게. 필요한 게 있거나 도와줄 일이 있으면 언제든지 말해줘.\n" +
                "###\n" +
                "사용자 이름: 이준재\n" +
                "사용자 소속: 광고대행사\n" +
                "사용자 직무: 주임\n" +
                "상대방 이름: 백희진\n" +
                "상대방 관계: 후배\n" +
                "문장: 진짜 일 이따위로 할래?? 아니 도대체ㅜㅜ 왤케 말귀를 못알아쳐먹냐.. 엑셀도 못하면서 도대체 어떻게 뽑힌거냐구요.. 지금 누락건에 함수실수가 몇개냐구..진짜 미치겠다.. 제발 정신좀 차리고 일해\n" +
                "변환: 희진아, 업무 중에 실수가 있어서 많이 당황스럽고 속상했을 것 같아. 그래도 누락 건과 함수 실수에 대해서는 확실한 확인이 필요할 것 같아. 정말 많이 쓰이는 부분이니 미리 알아두면 도움이 될거야. 앞으로는 조금더 꼼꼼하게, 확인했다고 생각해도 한번 더 꼼꼼하게 확인하는 절차를 밟았으면 해. 열심히 하고 있는거 충분히 알고 있으니까 차근하게 실수를 줄여 나가보자!\n" +
                "### \n" +
                "사용자 이름: 김민경\n" +
                "사용자 소속: 병원\n" +
                "사용자 직무: 간호조무사\n" +
                "상대방 이름: 은주희\n" +
                "상대방 관계: 지인\n" +
                "문장: 썅년아 내 돈 언제 갚을거임 진짜 이번달 생활비 박살났다고..진짜 니년 믿는게 아니었는데 믿은 내가 빙시지.. 진짜 이 연락 무시하면 끝이디 바로 고소 갈긴다. 옛정 생각해서 고소 전에 연락이라도 넣어주는거니까 정신차리고 보면 바로 답장해라.\n" +
                "변환: 주희야, 나 민경이야. 이번달 생활비를 내야하는데 아직 네게서 돈을 받지 못해 어려운 상황이야. 솔직히 네 사정 듣고, 또 우리 옛 추억 생각하며 믿고 빌려준 돈인데 이렇게 연락이 안되니 너무 실망스러워. 나도 상황이 상황인지라 돈을 받지 못하면 법적 절차를 밟게 될 것 같아. 연락 확인하면 바로 답장 부탁할게.\n";

        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", promptSystemMessage);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", createPromptUserMessage(member, chatRoom, userMessage));

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

        String resultMessage = clovaStudioApiExecutor.execute(requestData);
        Message messageEntity = saveBotMessage(chatRoom, resultMessage);
        chatRoom.updateLastUsedAt(messageEntity.getCreatedAt());

        return resultMessage;
    }

    private String createPromptUserMessage(Member member, ChatRoom chatRoom, String message) {
        StringBuffer sb = new StringBuffer();
        sb.append("사용자 이름: ").append(member.getRealName()).append("\n");
        sb.append("사용자 소속: ").append(member.getAffiliation()).append("\n");
        sb.append("사용자 직무: ").append(member.getJob()).append("\n");
        sb.append("상대방 이름: ").append(chatRoom.getPartnerName()).append("\n");
        sb.append("상대방 관계: ").append(chatRoom.getPartnerRel().getLabel()).append("\n");
        sb.append("문장: ").append(message).append("\n");
        return sb.toString();
    }

    private Message saveUserMessage(ChatRoom chatRoom, String resultMessage) {
        Message messageEntity = createUserMessageEntity(chatRoom, resultMessage);
        messageRepository.save(messageEntity);
        return messageEntity;
    }

    private Message saveBotMessage(ChatRoom chatRoom, String resultMessage) {
        Message messageEntity = createBotMessageEntity(chatRoom, resultMessage);
        messageRepository.save(messageEntity);
        messageRepository.flush();
        return messageEntity;
    }

    private Message createBotMessageEntity(ChatRoom chatRoom, String content) {
        Message message = Message.builder()
                .chatRoom(chatRoom)
                .content(content)
                .senderType(SenderType.BOT)
                .build();
        chatRoom.addMessage(message);
        return message;
    }

    private Message createUserMessageEntity(ChatRoom chatRoom, String content) {
        Message message = Message.builder()
                .chatRoom(chatRoom)
                .content(content)
                .senderType(SenderType.USER)
                .build();
        chatRoom.addMessage(message);
        return message;
    }
}
