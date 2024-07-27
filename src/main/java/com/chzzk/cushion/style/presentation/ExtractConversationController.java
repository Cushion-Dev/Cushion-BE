package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.global.exception.ErrorCode;
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

import java.util.List;

@Tag(name = "clova", description = "CLOVA 관련 API")
@RequestMapping("/ocr")
@RestController
@RequiredArgsConstructor
public class ExtractConversationController {

    private final ExtractConversationService extractConversationService;

    @Operation(summary = "OCR로 대화 내용 추출", description = "OCR을 이용해 이미지 속 대화 내용을 추출합니다.")
    @PostMapping
    public String extractConversation(@RequestPart("file") List<MultipartFile> multipartFiles) {
        if (multipartFiles.size() > 3) {
            throw new CushionException(ErrorCode.UPLOAD_FILES_SIZE_EXCEEDED);
        }
        return extractConversationService.extractConversation(multipartFiles);
    }
}
