package com.chzzk.cushion.chatroom.application;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.chatroom.domain.repository.ChatRoomRepository;
import com.chzzk.cushion.chatroom.domain.repository.MessageRepository;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomCreateRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomDeleteRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomUpdateRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse.ChatRoomDetailResponse;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse.ChatRoomSimpleResponse;
import com.chzzk.cushion.chatroom.dto.MessageDto.MessageRequest;
import com.chzzk.cushion.global.common.BaseTimeEntity;
import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.chzzk.cushion.global.exception.ErrorCode.NOT_FOUND_CHAT_ROOM_THAT_MEMBER;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public ChatRoomSimpleResponse create(ChatRoomCreateRequest chatRoomCreateRequest, ApiMember apiMember) {
        // 멤버 검증
        Member member = apiMember.toMember(memberRepository);

        String chatRoomTitle = "%s(%s)님과의 쿠션".formatted(chatRoomCreateRequest.getPartnerName(), chatRoomCreateRequest.getPartnerRel().getLabel());
        ChatRoom chatRoom = chatRoomCreateRequest.toEntity(member, chatRoomTitle);

        chatRoomRepository.save(chatRoom);
        return ChatRoomSimpleResponse.fromEntity(chatRoom);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponse> findAll(ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        return chatRoomRepository.findAllOrderByLastUsedAt(member);
    }

    @Transactional
    public void delete(ChatRoomDeleteRequest chatRoomDeleteRequest, ApiMember apiMember) {
        // 멤버 검증
        Member member = apiMember.toMember(memberRepository);

        // 채팅방 존재 검증
        List<Long> chatRoomIds = chatRoomDeleteRequest.getChatRoomIds();
        for (Long chatRoomId : chatRoomIds) {
            chatRoomRepository.findByIdAndMember(chatRoomId, member)
                    .orElseThrow(() -> new CushionException(NOT_FOUND_CHAT_ROOM_THAT_MEMBER));
        }
        chatRoomRepository.deleteByMemberAndIdIn(member, chatRoomDeleteRequest.getChatRoomIds());
    }

    @Transactional
    public void update(ChatRoomUpdateRequest chatRoomUpdateRequest, Long roomId, ApiMember apiMember) {

        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = chatRoomRepository.findByIdAndMember(roomId, member)
                .orElseThrow(() -> new CushionException(NOT_FOUND_CHAT_ROOM_THAT_MEMBER));

        chatRoom.updateInfo(chatRoomUpdateRequest.getPartnerName(), chatRoomUpdateRequest.getPartnerRel());
    }

    @Transactional(readOnly = true)
    public ChatRoomDetailResponse findById(Long roomId, ApiMember apiMember) {
        // 멤버 검증
        Member member = apiMember.toMember(memberRepository);

        // 채팅방-멤버 검증
        ChatRoom chatRoom = chatRoomRepository.findByIdAndMember(roomId, member)
                .orElseThrow(() -> new CushionException(NOT_FOUND_CHAT_ROOM_THAT_MEMBER));

        List<Message> messages = chatRoom.getMessages();
        messages.sort(Comparator.comparing(BaseTimeEntity::getCreatedAt));

        return ChatRoomDetailResponse.fromEntity(chatRoom, messages);

    }

    @Transactional
    public void saveMessage(MessageRequest messageRequest, Long roomId, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = chatRoomRepository.findByIdAndMember(roomId, member)
                .orElseThrow(() -> new CushionException(NOT_FOUND_CHAT_ROOM_THAT_MEMBER));

        Message message = messageRequest.toEntity(chatRoom);
        messageRepository.save(message);
        chatRoom.updateLastUsedAt(message.getCreatedAt());
    }
}
