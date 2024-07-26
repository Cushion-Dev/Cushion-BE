package com.chzzk.cushion.style.presentation;

import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.application.ChangeStyleWithOcrService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "style", description = "문체 변환 API")
@RequestMapping("/change-style/ocr")
@RestController
@RequiredArgsConstructor
public class ChangeStyleWithOcrController {

    private final ChangeStyleWithOcrService changeStyleWithOcrService;

    @GetMapping
    public String changeStyleWithOrc(@AuthPrincipal ApiMember apiMember,
                                     @RequestParam Long roomId,
                                     @RequestPart("file") List<MultipartFile> multipartFiles) {
        return changeStyleWithOcrService.changeStyleWithOcr(apiMember, roomId, multipartFiles);
    }
}
