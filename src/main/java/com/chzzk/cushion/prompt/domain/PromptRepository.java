package com.chzzk.cushion.prompt.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
    Optional<Prompt> findByType(PromptType type);
}
