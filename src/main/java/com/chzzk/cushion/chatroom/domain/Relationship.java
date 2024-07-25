package com.chzzk.cushion.chatroom.domain;

import static com.chzzk.cushion.global.exception.ErrorCode.INVALID_RELATIONSHIP;

import com.chzzk.cushion.global.exception.CushionException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonCreator
    public static Relationship from(String value) {
        for (Relationship relationship : Relationship.values()) {
            if (relationship.name().equals(value)) {
                return relationship;
            }
        }
        throw new CushionException(INVALID_RELATIONSHIP);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
