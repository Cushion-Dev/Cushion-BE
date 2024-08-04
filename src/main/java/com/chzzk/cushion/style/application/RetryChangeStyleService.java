package com.chzzk.cushion.style.application;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.domain.ClovaApiRequestDataGenerator;
import com.chzzk.cushion.style.domain.ClovaStudioApiExecutor;
import com.chzzk.cushion.style.dto.RetryChangeStyleRequest;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.chzzk.cushion.chatroom.domain.SenderType.BOT;
import static com.chzzk.cushion.chatroom.domain.SenderType.USER;

@Service
@RequiredArgsConstructor
public class RetryChangeStyleService {

    private final MemberRepository memberRepository;
    private final ClovaApiRequestDataGenerator clovaApiRequestDataGenerator;
    private final ClovaStudioApiExecutor clovaStudioApiExecutor;

    @Transactional
    public String retryChangeStyle(ApiMember apiMember, RetryChangeStyleRequest request) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(request.getRoomId());
        Message latestUserMessage = chatRoom.getLatestMessage(USER);

        JSONObject requestData = clovaApiRequestDataGenerator
                .generateWithUserMessage(member, latestUserMessage.getContent(), chatRoom);
        String resultMessage = clovaStudioApiExecutor.changeStyleDefault(requestData);

        Message latestBotMessage = chatRoom.getLatestMessage(BOT);
        latestBotMessage.updateContent(resultMessage);

        return resultMessage;
    }

    @Transactional
    public String retryChangeStyleWithPersonality(ApiMember apiMember, RetryChangeStyleRequest request) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(request.getRoomId());
        Message latestUserMessage = chatRoom.getLatestMessage(USER);

        JSONObject requestData = clovaApiRequestDataGenerator
                .generateWithUserMessageAndPersonality(member, latestUserMessage.getContent(), chatRoom);
        String resultMessage = clovaStudioApiExecutor.changeStyleDefault(requestData);

        Message latestBotMessage = chatRoom.getLatestMessage(BOT);
        latestBotMessage.updateContent(resultMessage);

        return resultMessage;
    }
}
