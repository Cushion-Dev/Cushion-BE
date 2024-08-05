package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.application.RetryChangeStyleService;
import com.chzzk.cushion.style.dto.RetryChangeStyleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "clova", description = "CLOVA 관련 API")
@RequestMapping("/change-style")
@RestController
@RequiredArgsConstructor
public class RetryChangeStyleController {

    private final RetryChangeStyleService retryChangeStyleService;

    @Operation(summary = "문체 변환 새로고침", description = "문체 변환을 다시 시도합니다.")
    @PostMapping("/retry")
    public String changeStyle(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember,
                              @Valid @RequestBody RetryChangeStyleRequest request) {
        return retryChangeStyleService.retryChangeStyle(apiMember, request);
    }
}
