package com.chzzk.cushion.chatroom.presentation;

import com.chzzk.cushion.chatroom.application.ChatRoomService;
import com.chzzk.cushion.chatroom.dto.ChatRoomDetailResponse;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "chat_room", description = "채팅방 API")
@RequestMapping("/chat/rooms")
@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성", description = "채팅방을 생성합니다.")
    @PostMapping
    public void createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest, @AuthPrincipal ApiMember apiMember) {
        chatRoomService.create(chatRoomRequest, apiMember);
    }

    @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회합니다.")
    @GetMapping
    public List<ChatRoomResponse> findAllChatRooms() {
        return chatRoomService.findAll();
    }

    @Operation(summary = "채팅방 상세 조회", description = "채팅방 상세 페이지를 조회합니다.")
    @GetMapping("/{roomId}")
    public ChatRoomDetailResponse findChatRoomDetail(@PathVariable long roomId) {
        return chatRoomService.findById();
    }

    @Operation(summary = "채팅방 수정", description = "채팅방에 대한 정보를 수정합니다.")
    @PutMapping("/{roomId}")
    public void updateChatRoom(@PathVariable long roomId, @RequestBody ChatRoomRequest request) {
        chatRoomService.update();
    }

    @Operation(summary = "채팅방 삭제", description = "채팅방을 삭제합니다.")
    @DeleteMapping("/{roomId}")
    public void deleteChatRoom(@PathVariable long roomId) { // TODO 회원 dto 추가
        chatRoomService.delete();
    }
}
