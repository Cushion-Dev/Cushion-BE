package com.chzzk.cushion.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "사용자 설정 요청")
public class MemberDto {

    @Schema(description = "소속", example = "쿠션 회사")
    private String affiliation;

    @Schema(description = "직무", example = "개발")
    private String job;

    @Schema(description = "이름", example = "홍길동")
    private String realName;
}
