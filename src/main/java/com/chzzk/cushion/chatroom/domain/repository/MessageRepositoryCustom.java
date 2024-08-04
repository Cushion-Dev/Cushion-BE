package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.domain.Message;

public interface MessageRepositoryCustom {

    Message findLatestMessageByChatRoomId(Long chatRoomId);
}
