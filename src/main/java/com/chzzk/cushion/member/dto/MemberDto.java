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
    @NotBlank(message = "소속은 공백을 허용하지 않습니다.")
    @Size(max = 15, message = "소속 글자수는 0에서 15 사이여야 합니다.")
    private String affiliation;

    @Schema(description = "직무", example = "개발")
    @NotBlank(message = "직무는 공백을 허용하지 않습니다.")
    @Size(max = 15, message = "직무 글자수는 0에서 15 사이여야 합니다.")
    private String job;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 공백을 허용하지 않습니다.")
    @Size(max = 15, message = "이름 글자수는 0에서 15 사이여야 합니다.")
    private String realName;
}
