package com.chzzk.cushion.chatroom.application;

import static com.chzzk.cushion.global.exception.ErrorCode.NOT_FOUND_CHAT_ROOM_THAT_MEMBER;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.chatroom.domain.repository.ChatRoomRepository;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse.ChatRoomDetailResponse;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomCreateRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomDeleteRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(ChatRoomCreateRequest chatRoomCreateRequest, ApiMember apiMember) {
        // 멤버 검증
        Member member = apiMember.toMember(memberRepository);

        String chatRoomTitle = chatRoomCreateRequest.getPartnerName() + "(" + chatRoomCreateRequest.getPartnerRel().getLabel() + ")" + "님과의 쿠션";
        ChatRoom chatRoom = chatRoomCreateRequest.toEntity(member, chatRoomTitle);

        chatRoomRepository.save(chatRoom);
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
    public void update() {

    }

    @Transactional(readOnly = true)
    public ChatRoomDetailResponse findById(Long roomId, ApiMember apiMember) {
        // 멤버 검증
        Member member = apiMember.toMember(memberRepository);

        // 채팅방-멤버 검증
        ChatRoom chatRoom = chatRoomRepository.findByIdAndMember(roomId, member)
                .orElseThrow(() -> new CushionException(NOT_FOUND_CHAT_ROOM_THAT_MEMBER));

        List<Message> messages = chatRoom.getMessages();

        return ChatRoomDetailResponse.fromEntity(chatRoom, messages);

    }
}
