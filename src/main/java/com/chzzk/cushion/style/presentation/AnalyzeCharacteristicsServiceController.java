package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.application.AnalyzeCharacteristicsService;
import com.chzzk.cushion.style.dto.AnalyzeCharacteristicsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "style", description = "문체 변환 API")
@RequestMapping("/characteristics")
@RestController
@RequiredArgsConstructor
public class AnalyzeCharacteristicsServiceController {

    private final AnalyzeCharacteristicsService analyzeCharacteristicsService;

    @Operation(summary = "OCR로 대화 내용 추출 및 성격 분석", description = "OCR을 이용해 이미지 속 텍스트를 추출하고, 성격을 분석합니다.")
    @PostMapping
    public String analyzeTendency(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember,
                                  @RequestBody AnalyzeCharacteristicsRequest analyzeCharacteristicsRequest) {
        return analyzeCharacteristicsService.analyzeCharacteristics(apiMember, analyzeCharacteristicsRequest);
    }
}
