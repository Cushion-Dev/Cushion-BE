package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.chatroom.dto.QChatRoomResponse;
import com.chzzk.cushion.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
                .where(chatRoom.member.id.eq(member.getId()), chatRoom.lastUsedAt.eq(message.createdAt))
                .orderBy(chatRoom.lastUsedAt.desc())
                .fetch();
    }

    @Override
    public List<ChatRoomResponse> searchByTitle(Member member, String query) {
        return queryFactory
                .select(new QChatRoomResponse(
                        chatRoom.id, chatRoom.partnerName, chatRoom.partnerRel, message.content, chatRoom.lastUsedAt
                ))
                .from(chatRoom)
                .join(message).on(chatRoom.id.eq(message.chatRoom.id))
                .where(chatRoom.member.id.eq(member.getId()),
                        chatRoom.lastUsedAt.eq(message.createdAt),
                        chatRoom.title.contains(query))
                .orderBy(chatRoom.lastUsedAt.desc())
                .fetch();
    }
}
