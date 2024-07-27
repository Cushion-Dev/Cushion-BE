package com.chzzk.cushion.chatroom.dto;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.chatroom.domain.Relationship;
import com.chzzk.cushion.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ChatRoomRequest {

    @Schema(description = "채팅방 생성 요청")
    @Data
    @AllArgsConstructor
    public static class ChatRoomCreateRequest {

        @Schema(description = "상대방 이름", example = "김철수")
        @NotBlank(message = "상대방 이름은 공백을 허용하지 않습니다.")
        @Size(max = 15, message = "상대방 이름 글자수는 0에서 15 사이여야 합니다.")
        private String partnerName;

        @Schema(description = "상대방 관계", example = "FRIEND")
        private Relationship partnerRel;

        public ChatRoom toEntity(Member member, String chatRoomTitle) {
            return ChatRoom.builder()
                    .member(member)
                    .partnerName(partnerName)
                    .partnerRel(partnerRel)
                    .title(chatRoomTitle)
                    .build();
        }
    }

    @Schema(description = "채팅방 삭제 요청")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatRoomDeleteRequest {

        @Schema(description = "삭제할 채팅방 ID 목록", example = "[1, 2, 3]")
        private List<Long> chatRoomIds;

    }

    @Schema(description = "채팅방 수정 요청")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatRoomUpdateRequest {

        @Schema(description = "수정할 상대방 이름", example = "김철수")
        @NotBlank(message = "상대방 이름은 공백을 허용하지 않습니다.")
        @Size(max = 15, message = "상대방 이름 글자수는 0에서 15 사이여야 합니다.")
        private String partnerName;

        @Schema(description = "수정할 상대방 관계", example = "FRIEND")
        private Relationship partnerRel;
    }
}
