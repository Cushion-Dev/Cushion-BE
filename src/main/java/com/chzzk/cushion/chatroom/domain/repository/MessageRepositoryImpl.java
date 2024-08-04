package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.domain.Message;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.chzzk.cushion.chatroom.domain.QMessage.message;
import static com.chzzk.cushion.chatroom.domain.SenderType.BOT;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Message findLatestMessageByChatRoomId(Long chatRoomId) {
        return queryFactory
                .selectFrom(message)
                .where(message.chatRoom.id.eq(chatRoomId), message.senderType.eq(BOT))
                .orderBy(message.createdAt.desc())
                .limit(1)
                .fetchOne();
    }
}
