package com.chzzk.cushion.style.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "문체 변환 새로고침 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetryChangeStyleRequest {

    @Schema(description = "채팅방 ID", example = "1")
    @NotNull(message = "채팅방 ID는 필수입니다.")
    private Long roomId;

    @Schema(description = "성격 포함 여부", example = "false")
    @NotNull(message = "성격 포함 여부는 필수입니다.")
    private Boolean withPersonality;

    public boolean withPersonality() {
        return withPersonality;
    }
}
