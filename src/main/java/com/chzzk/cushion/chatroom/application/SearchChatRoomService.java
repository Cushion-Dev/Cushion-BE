package com.chzzk.cushion.chatroom.application;

import com.chzzk.cushion.chatroom.domain.repository.ChatRoomRepository;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoomResponse> searchByTitle(String query) {
        return chatRoomRepository.searchByTitle(query);
    }
}
