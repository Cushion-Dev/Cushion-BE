package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
