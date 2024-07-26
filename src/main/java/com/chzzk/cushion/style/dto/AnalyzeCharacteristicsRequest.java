package com.chzzk.cushion.style.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "성격 분석 요청")
@Data
@AllArgsConstructor
public class AnalyzeCharacteristicsRequest {

    @Schema(description = "채팅방 ID", example = "1")
    private Long roomId;

    @Schema(description = "대화 내용", example = "HTTPS 연결 작업도 사실 언제 끝날지 미지수고\n" +
            "OCR을 저희가 한다 칩시다\n" +
            "오전 12:33\n" +
            "그러면 QA를 못할 수도 있는데\n" +
            "괜찮으신가요?\n" +
            "오전 12:34")
    private String conversation;
}
