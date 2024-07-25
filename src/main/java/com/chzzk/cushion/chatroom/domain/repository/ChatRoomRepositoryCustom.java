package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.member.domain.Member;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomResponse> findAllOrderByLastUsedAt(Member member);

    List<ChatRoomResponse> searchByTitle(String query);
}
