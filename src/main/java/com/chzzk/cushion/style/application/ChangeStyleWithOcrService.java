package com.chzzk.cushion.style.application;

import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.domain.ClovaOcrApiExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeStyleWithOcrService {

    private final ClovaOcrApiExecutor clovaOcrApiExecutor;

    public String changeStyleWithOcr(ApiMember apiMember, List<MultipartFile> multipartFiles) {
        return clovaOcrApiExecutor.execute(multipartFiles);
    }
}
