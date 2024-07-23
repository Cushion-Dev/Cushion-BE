package com.chzzk.cushion.chatroom.domain;

import lombok.Getter;

@Getter
public enum Relationship {
    PARENT("(조)부모"),
    SIBLING("형제/자매"),
    SPOUSE("배우자"),
    CHILDREN("자녀"),
    FRIEND("친구"),
    BOSS("상사"),
    COLLEAGUE("동료"),
    JUNIOR("후배"),
    ACQUAINTANCE("지인");

    private final String label;

    Relationship(String label) {
        this.label = label;
    }
}
