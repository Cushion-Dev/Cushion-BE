package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomResponse> findAllOrderByLastUsedAt();

    List<ChatRoomResponse> searchByTitle(String query);
}
