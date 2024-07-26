package com.chzzk.cushion.style.application;

import com.chzzk.cushion.style.domain.ClovaOcrApiExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtractConversationService {

    private final ClovaOcrApiExecutor clovaOcrApiExecutor;

    public String extractConversation(List<MultipartFile> multipartFiles) {
        return clovaOcrApiExecutor.execute(multipartFiles);
    }
}
