package com.chzzk.cushion.style.application;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.domain.ClovaStudioApiExecutor;
import com.chzzk.cushion.style.domain.RetryChangeStyleRequestDataGenerator;
import com.chzzk.cushion.style.dto.RetryChangeStyleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chzzk.cushion.chatroom.domain.SenderType.BOT;
import static com.chzzk.cushion.chatroom.domain.SenderType.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryChangeStyleService {

    private final MemberRepository memberRepository;
    private final RetryChangeStyleRequestDataGenerator retryChangeStyleRequestDataGenerator;
    private final ClovaStudioApiExecutor clovaStudioApiExecutor;

    @Transactional
    public String retryChangeStyle(ApiMember apiMember, RetryChangeStyleRequest request) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(request.getRoomId());
        Message latestUserMessage = chatRoom.getLatestMessage(USER);
        Message latestBotMessage = chatRoom.getLatestMessage(BOT);

        JSONObject requestData = getRequestData(request.withPersonality(), member, chatRoom, latestUserMessage, latestBotMessage);
        log.info("requestData = {}", requestData.toJSONString());

        String resultMessage = clovaStudioApiExecutor.changeStyleDefault(requestData);

        latestBotMessage.updateContent(resultMessage);

        return resultMessage;
    }

    private JSONObject getRequestData(boolean withPersonality,
                                      Member member,
                                      ChatRoom chatRoom,
                                      Message latestUserMessage,
                                      Message latestBotMessage) {
        return withPersonality ?
                getRequestDataWithPersonality(member, chatRoom, latestUserMessage, latestBotMessage) :
                getRequestData(member, chatRoom, latestUserMessage, latestBotMessage);
    }

    private JSONObject getRequestDataWithPersonality(Member member, ChatRoom chatRoom, Message latestUserMessage, Message latestBotMessage) {
        return retryChangeStyleRequestDataGenerator
                .generateWithUserMessageAndPersonality(
                        member,
                        latestUserMessage.getContent(),
                        latestBotMessage.getContent(),
                        chatRoom);
    }

    private JSONObject getRequestData(Member member, ChatRoom chatRoom, Message latestUserMessage, Message latestBotMessage) {
        return retryChangeStyleRequestDataGenerator
                .generateWithUserMessage(
                        member,
                        latestUserMessage.getContent(),
                        latestBotMessage.getContent(),
                        chatRoom);
    }
}
