package com.chzzk.cushion.chatroom.dto;

import com.chzzk.cushion.chatroom.domain.Relationship;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "채팅방 생성/수정 요청")
@Data
@AllArgsConstructor
public class ChatRoomRequest {

    @Schema(description = "상대방 이름", example = "김철수")
    private String partnerName;

    @Schema(description = "상대방 관계", example = "FRIEND")
    private Relationship relationship;
}
