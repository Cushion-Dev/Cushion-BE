package com.chzzk.cushion.chatroom.presentation;

import com.chzzk.cushion.chatroom.application.ChatRoomService;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomUpdateRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse.ChatRoomDetailResponse;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomCreateRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomRequest.ChatRoomDeleteRequest;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse;
import com.chzzk.cushion.chatroom.dto.ChatRoomResponse.ChatRoomSimpleResponse;
import com.chzzk.cushion.chatroom.dto.MessageDto.MessageRequest;
import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public ChatRoomSimpleResponse createChatRoom(@Valid @RequestBody ChatRoomCreateRequest chatRoomCreateRequest,
                                                 @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        return chatRoomService.create(chatRoomCreateRequest, apiMember);
    }

    @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회합니다.")
    @GetMapping
    public List<ChatRoomResponse> findAllChatRooms(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        return chatRoomService.findAll(apiMember);
    }

    @Operation(summary = "채팅방 상세 조회", description = "채팅방 상세 페이지를 조회합니다.")
    @GetMapping("/{roomId}")
    public ChatRoomDetailResponse findChatRoomDetail(@PathVariable long roomId,
                                                     @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        return chatRoomService.findById(roomId, apiMember);
    }


    @Operation(summary = "채팅방 수정", description = "채팅방에 대한 정보를 수정합니다.")
    @PutMapping("/{roomId}")
    public void updateChatRoom(@PathVariable long roomId, @Valid @RequestBody ChatRoomUpdateRequest chatRoomUpdateRequest,
                               @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        chatRoomService.update(chatRoomUpdateRequest, roomId, apiMember);
    }

    @Operation(summary = "채팅방 삭제", description = "채팅방을 삭제합니다.")
    @PostMapping("/delete")
    public void deleteChatRoom(@RequestBody ChatRoomDeleteRequest chatRoomDeleteRequest,
                               @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) { // TODO 회원 dto 추가
        chatRoomService.delete(chatRoomDeleteRequest, apiMember);
    }

    @Operation(summary = "메시지 저장", description = "채팅방에 메시지를 저장합니다.")
    @PostMapping("/{roomId}/message")
    public void saveMessage(@PathVariable Long roomId, @RequestBody MessageRequest messageRequest,
                            @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        chatRoomService.saveMessage(messageRequest, roomId, apiMember);
    }
}
