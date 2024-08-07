package com.chzzk.cushion.prompt.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String systemInstruction;

    @Column(columnDefinition = "TEXT")
    private String systemExample;

    @Enumerated(EnumType.STRING)
    private PromptType type;

    public String getSystemMessage() {
        return "%s\n\n%s".formatted(systemInstruction, systemExample);
    }
}
