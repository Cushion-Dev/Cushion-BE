package com.chzzk.cushion.chatroom.domain.repository;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

    @Modifying
    @Transactional
    void deleteByMemberAndIdIn(Member member, List<Long> ids);

    Optional<ChatRoom> findByIdAndMember(Long id, Member member);
}
