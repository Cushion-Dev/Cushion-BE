package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.style.application.ExtractConversationTextWithOcrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "ocr", description = "CLOVA OCR API")
@RequestMapping("/ocr")
@RestController
@RequiredArgsConstructor
public class ExtractConversationTextWithOcrController {

    private final ExtractConversationTextWithOcrService extractConversationTextWithOcrService;

    @Operation(summary = "OCR을 이용한 텍스트 추출", description = "OCR을 이용해 이미지 속 텍스트를 추출합니다.")
    @GetMapping("/extract")
    public String extractConversationTextWithOcr(@RequestPart("file") List<MultipartFile> multipartFiles) {
        return extractConversationTextWithOcrService.extractConversationText(multipartFiles);
    }
}
