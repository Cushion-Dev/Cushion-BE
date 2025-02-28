package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.application.ChangeStyleService;
import com.chzzk.cushion.style.dto.ChangeStyleRequest;
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
public class ChangeStyleController {

    private final ChangeStyleService changeStyleService;

    @Operation(summary = "문체 변환", description = "문체를 변환합니다.")
    @PostMapping
    public String changeStyle(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember,
                              @Valid @RequestBody ChangeStyleRequest changeStyleRequest) {
        return changeStyleService.changeStyle(apiMember, changeStyleRequest);
    }
}
