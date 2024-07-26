package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.style.application.ExtractConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Tag(name = "style", description = "문체 변환 API")
@RequestMapping("/ocr")
@RestController
@RequiredArgsConstructor
public class ExtractConversationController {

    private final ExtractConversationService extractConversationService;

    @Operation(summary = "OCR로 대화 내용 추출", description = "OCR을 이용해 이미지 속 대화 내용을 추출합니다.")
    @PostMapping
    public String extractConversation(@RequestPart("file") List<MultipartFile> multipartFiles) {
        log.info("size = {}, filesArray = {}", multipartFiles.size(), Arrays.toString(multipartFiles.toArray()));
        return extractConversationService.extractConversation(multipartFiles);
    }
}
