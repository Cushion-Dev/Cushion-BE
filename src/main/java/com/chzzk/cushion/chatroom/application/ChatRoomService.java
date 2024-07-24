package com.chzzk.cushion.chatroom.application;

import com.chzzk.cushion.chatroom.domain.repository.ChatRoomRepository;
import com.chzzk.cushion.chatroom.domain.SenderType;
import com.chzzk.cushion.chatroom.dto.ChatRoomDetailResponse;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.chatroom.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void create() {

    }

    public List<ChatRoomResponse> findAll() {
        return chatRoomRepository.findAllOrderByLastUsedAt();
    }

    @Transactional
    public void delete() {

    }

    @Transactional
    public void update() {

    }

    public ChatRoomDetailResponse findById() {
        MessageDto m1 = new MessageDto(1L, SenderType.BOT, "안녕하세요! 저는 지금부터 ‘홍길동(상사)’ 님의 입장에서 사용자님의 메시지가 좀 더 부드러워지도록 도와드릴게요\uD83D\uDE1A\u2028\u2028부드럽게 쿠션을 만드시려는 메시지 내용을 입력해주세요.");
        MessageDto m2 = new MessageDto(2L, SenderType.USER, "제가 몸이 조금 안 좋아서 일찍 들어가봐도 될까요? 오전에 지시하신 업무는 다 완료했습니다.");
        return new ChatRoomDetailResponse("김철수", "친구", List.of(m1, m2), LocalDate.of(2023, 6, 3), LocalDate.of(2024, 12, 3));
    }
}
