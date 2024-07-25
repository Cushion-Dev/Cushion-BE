package com.chzzk.cushion.chatroom.dto;

import static com.chzzk.cushion.chatroom.domain.SenderType.BOT;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Message;
import com.chzzk.cushion.chatroom.domain.SenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MessageDto {

    @NoArgsConstructor
    @Builder
    @Schema(description = "메시지 저장 요청")
    public static class MessageRequest {

        @Schema(description = "서버에 저장될 메시지 내용", example = "안녕하세요! 저는 지금부터 ‘홍길동(상사)’ 님의 입장에서 ...")
        private String content;

        public Message toEntity(ChatRoom chatRoom) {
            return Message.builder()
                    .chatRoom(chatRoom)
                    .content(content)
                    .senderType(BOT)
                    .build();
        }
    }

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
