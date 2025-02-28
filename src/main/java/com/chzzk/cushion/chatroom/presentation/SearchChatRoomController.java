package com.chzzk.cushion.chatroom.presentation;

import com.chzzk.cushion.chatroom.application.SearchChatRoomService;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "채팅방 검색", description = "채팅방 제목(상대방 이름 및 관계)을 통해 채팅방을 검색합니다.")
    @GetMapping
    public List<ChatRoomResponse> searchChatRoomsByTitle(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember,
                                                         @RequestParam String query) {
        return searchChatRoomService.searchByTitle(apiMember, query);
    }
}
