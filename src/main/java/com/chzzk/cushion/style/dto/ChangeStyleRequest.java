package com.chzzk.cushion.style.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "문체 변환 요청")
@Data
@AllArgsConstructor
public class ChangeStyleRequest {

    @Schema(description = "채팅방 ID", example = "1")
    @NotNull(message = "채팅방 ID는 필수입니다.")
    private Long roomId;

    @Schema(description = "변환할 문장", example = "안녕하세요. 이것좀 변환해주삼")
    @NotBlank(message = "변환할 문장은 공백을 허용하지 않습니다.")
    @Size(max = 400, message = "변환할 문장의 길이는 0에서 400 사이여야 합니다.")
    private String userMessage;

    @Schema(description = "성격 포함 여부", example = "false")
    @NotNull(message = "성격 포함 여부는 필수입니다.")
    private Boolean withPersonality;

    public boolean withPersonality() {
        return withPersonality;
    }
}
