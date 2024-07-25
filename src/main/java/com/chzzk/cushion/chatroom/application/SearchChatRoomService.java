package com.chzzk.cushion.chatroom.application;

import com.chzzk.cushion.chatroom.domain.repository.ChatRoomRepository;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public List<ChatRoomResponse> searchByTitle(ApiMember apiMember, String query) {
        Member member = apiMember.toMember(memberRepository);
        return chatRoomRepository.searchByTitle(member, query);
    }
}
