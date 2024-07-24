package com.chzzk.cushion.chatroom.presentation;

import com.chzzk.cushion.chatroom.application.SearchChatRoomService;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "chat_room", description = "채팅방 API")
@RequestMapping("/chat/rooms/search")
@RestController
@RequiredArgsConstructor
public class SearchChatRoomController {

    private final SearchChatRoomService searchChatRoomService;

    @Operation(summary = "채팅방 검색", description = "상대방 이름을 통해 채팅방을 검색합니다.")
    @GetMapping
    public List<ChatRoomResponse> searchChatRooms(@RequestParam String query) {
        return searchChatRoomService.searchChatRoom();
    }
}
