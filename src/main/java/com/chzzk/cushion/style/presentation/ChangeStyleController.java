package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.application.ChangeStyleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "style", description = "문체 변환 API")
@RequestMapping("/change-style")
@RestController
@RequiredArgsConstructor
public class ChangeStyleController {

    private final ChangeStyleService changeStyleService;

    @Operation(summary = "문체 변환", description = "문체를 변환합니다.")
    @GetMapping
    public String changeStyle(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember,
                              @RequestParam Long roomId, @RequestParam String message) {
        return changeStyleService.changeStyle(apiMember, roomId, message);
    }
}
