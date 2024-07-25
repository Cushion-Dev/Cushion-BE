package com.chzzk.cushion.chatroom.dto;

import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.chatroom.domain.SenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class MessageDto {

    @Data
    @AllArgsConstructor
    @Builder
    @Schema(description = "메시지 정보 응답")
    public static class MessageResponse {

        @Schema(description = "메시지 ID", example = "1")
        private Long messageId;

        @Schema(description = "전송자 타입", example = "BOT / USER")
        private SenderType senderType;

        @Schema(description = "내용", example = "오전에 주신 업무 다 완료했습니다! 혹시 오늘 몸 상태가 조금 좋지 않아서 그런데 ...")
        private String content;

        public static MessageResponse fromEntity(Message message) {
            return MessageResponse.builder()
                    .messageId(message.getId())
                    .senderType(message.getSenderType())
                    .content(message.getContent())
                    .build();
        }
    }
}
