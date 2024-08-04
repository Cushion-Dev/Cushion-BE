package com.chzzk.cushion.style.application;


import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.chatroom.domain.SenderType;
import com.chzzk.cushion.chatroom.domain.repository.MessageRepository;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.domain.ClovaApiRequestDataGenerator;
import com.chzzk.cushion.style.domain.ClovaStudioApiExecutor;
import com.chzzk.cushion.style.dto.ChangeStyleRequest;
import com.chzzk.cushion.style.dto.ChangeStyleWithPersonalityRequest;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeStyleService {

    private final ClovaStudioApiExecutor clovaStudioApiExecutor;
    private final ClovaApiRequestDataGenerator clovaApiRequestDataGenerator;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public String changeStyle(ApiMember apiMember, ChangeStyleRequest request) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(request.getRoomId());

        // 사용자가 입력한 변환 전 메시지 저장
        saveUserMessage(chatRoom, request.getUserMessage());

        JSONObject requestData = clovaApiRequestDataGenerator
                .generateWithUserMessage(member, request.getUserMessage(), chatRoom);
        String resultMessage = clovaStudioApiExecutor.changeStyleDefault(requestData);

        Message messageEntity = saveBotMessage(chatRoom, resultMessage);
        chatRoom.updateLastUsedAt(messageEntity.getCreatedAt());

        return resultMessage;
    }

    private void saveUserMessage(ChatRoom chatRoom, String resultMessage) {
        Message messageEntity = createUserMessageEntity(chatRoom, resultMessage);
        messageRepository.save(messageEntity);
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

    @Transactional
    public String changeStyleWithPersonality(ApiMember apiMember,
                                             ChangeStyleWithPersonalityRequest request) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(request.getRoomId());

        // 사용자가 입력한 변환 전 메시지 저장
        saveUserMessage(chatRoom, request.getUserMessage());

        JSONObject requestData = clovaApiRequestDataGenerator
                .generateWithUserMessageAndPersonality(member, request.getUserMessage(), request.getPersonality(), chatRoom);
        String resultMessage = clovaStudioApiExecutor.changeStyleDefault(requestData);

        Message messageEntity = saveBotMessage(chatRoom, resultMessage);
        chatRoom.updateLastUsedAt(messageEntity.getCreatedAt());

        return resultMessage;
    }
}
