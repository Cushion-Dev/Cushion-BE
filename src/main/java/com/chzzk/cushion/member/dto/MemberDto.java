package com.chzzk.cushion.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "사용자 설정 요청")
public class MemberDto {

    @Schema(description = "소속", example = "쿠션 회사")
    @NotBlank
    @Size(max = 15)
    private String affiliation;

    @Schema(description = "직무", example = "개발")
    @NotBlank
    @Size(max = 15)
    private String job;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank
    @Size(max = 15)
    private String realName;
}
