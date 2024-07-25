package com.chzzk.cushion.chatroom.dto;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.chatroom.domain.Relationship;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "채팅방 목록 조회 응답")
@Data
public class ChatRoomResponse {

    @Schema(description = "채팅방 ID", example = "1")
    private long roomId;

    @Schema(description = "상대방 이름", example = "김철수")
    private String partnerName;

    @Schema(description = "상대방 관계", example = "동료")
    private String relationship;

    @Schema(description = "마지막 메시지", example = "오전에 주신 업무 다 완료했습니다! 혹시 오늘 몸 상태가 조금 좋지 않아서 그런데 ...")
    private String lastMessage;

    @Schema(description = "최근 이용일", example = "2024/04/16")
    private LocalDateTime lastUsedAt;

    @QueryProjection
    public ChatRoomResponse(long roomId, String partnerName, Relationship relationship, String lastMessage, LocalDateTime lastUsedAt) {
        this.roomId = roomId;
        this.partnerName = partnerName;
        this.relationship = relationship.getLabel();
        this.lastMessage = lastMessage.replace("\n", "");
        this.lastUsedAt = lastUsedAt;
    }

    @Schema(description = "채팅방 상세 조회 응답")
    @Data
    @Builder
    @AllArgsConstructor
    public static class ChatRoomDetailResponse {

        @Schema(description = "상대방 이름", example = "김철수")
        private String partnerName;

        @Schema(description = "상대방 관계", example = "동료")
        private String relationship;

        @Schema(description = "메시지 목록")
        private List<Message> messages;

        @Schema(description = "채팅방 생성일", example = "2024/05/30")
        private LocalDate createdAt;

        @Schema(description = "최근 이용일", example = "2024/05/30")
        private LocalDate lastUsedAt;

        public static ChatRoomDetailResponse fromEntity(ChatRoom chatRoom, List<Message> messages) {
            return ChatRoomDetailResponse.builder()
                    .partnerName(chatRoom.getPartnerName())
                    .relationship(chatRoom.getPartnerRel().getLabel())
                    .messages(messages)
                    .createdAt(chatRoom.getCreatedAt() == null ? null : chatRoom.getCreatedAt().toLocalDate())
                    .lastUsedAt(chatRoom.getLastUsedAt() == null ? null : chatRoom.getLastUsedAt().toLocalDate())
                    .build();
        }
    }
}
