package com.chzzk.cushion.chatroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "채팅방 상세 조회 응답")
@Data
@AllArgsConstructor
public class ChatRoomDetailResponse {

    @Schema(description = "상대방 이름", example = "김철수")
    private String partnerName;

    @Schema(description = "상대방 관계", example = "동료")
    private String relationship;

    @Schema(description = "메시지 목록")
    private List<MessageDto> messages;

    @Schema(description = "채팅방 생성일", example = "2024/05/30")
    private LocalDate createdAt;

    @Schema(description = "최근 이용일", example = "2024/05/30")
    private LocalDate lastUsedAt;
}
