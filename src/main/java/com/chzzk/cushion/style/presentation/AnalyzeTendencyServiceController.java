package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.application.AnalyzeTendencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "style", description = "문체 변환 API")
@RequestMapping("/tendency")
@RestController
@RequiredArgsConstructor
public class AnalyzeTendencyServiceController {

    private final AnalyzeTendencyService analyzeTendencyService;

    @Operation(summary = "OCR로 대화 내용 추출 및 성향 파악", description = "OCR을 이용해 이미지 속 텍스트를 추출하고, 성향을 분석합니다.")
    @GetMapping("/extract")
    public String analyzeTendency(@Parameter(hidden = true) @AuthPrincipal ApiMember apiMember,
                                  @RequestParam Long roomId,
                                  @RequestPart("file") List<MultipartFile> multipartFiles) {
        return analyzeTendencyService.analyzeTendency(apiMember, roomId, multipartFiles);
    }
}
