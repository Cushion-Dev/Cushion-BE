package com.chzzk.cushion.style.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "상대방 성격을 이용한 문체 변환 요청")
@Data
@AllArgsConstructor
public class ChangeStyleWithCharacteristicsRequest {

    @Schema(description = "채팅방 ID", example = "1")
    private long roomId;

    @Schema(description = "상대방 성향", example = "적극적이고 논리적인 성격으로 보입니다. ...")
    private String characteristics;

    @Schema(description = "변환할 문장", example = "안녕하세요. 이것좀 변환해주삼")
    private String userMessage;
}
