package com.chzzk.cushion.prompt.domain;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromptReader {

    private final PromptRepository promptRepository;

    public Prompt readPrompt(PromptType type) {
        return promptRepository.findByType(type)
                .orElseThrow(() -> new CushionException(ErrorCode.NOT_FOUND_PROMPT_FOR_TYPE));
    }
}
