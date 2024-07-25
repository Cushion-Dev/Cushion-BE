package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.chatroom.dto.QChatRoomResponse;
import com.chzzk.cushion.member.domain.Member;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.chzzk.cushion.chatroom.domain.QChatRoom.chatRoom;
import static com.chzzk.cushion.chatroom.domain.QMessage.message;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomResponse> findAllOrderByLastUsedAt(Member member) {
        return queryFactory
                .select(new QChatRoomResponse(
                        chatRoom.id, chatRoom.partnerName, chatRoom.partnerRel, message.content, chatRoom.lastUsedAt
                ))
                .from(chatRoom)
                .join(message).on(chatRoom.id.eq(message.chatRoom.id))
                .where(chatRoom.member.id.eq(member.getId()), toDate(chatRoom.lastUsedAt).eq(toDate(message.createdAt)))
                .orderBy(chatRoom.lastUsedAt.desc())
                .fetch();
    }

    private StringTemplate toDate(DateTimePath<LocalDateTime> localDateTime) {
        return Expressions.stringTemplate("DATE({0})", localDateTime);
    }

    @Override
    public List<ChatRoomResponse> searchByTitle(String query) {
        return queryFactory
                .select(new QChatRoomResponse(
                        chatRoom.id, chatRoom.partnerName, chatRoom.partnerRel, message.content, chatRoom.lastUsedAt
                ))
                .from(chatRoom)
                .join(message).on(chatRoom.id.eq(message.chatRoom.id))
                .where(chatRoom.lastUsedAt.eq(message.createdAt), chatRoom.title.contains(query))
                .orderBy(chatRoom.lastUsedAt.desc())
                .fetch();
    }
}
