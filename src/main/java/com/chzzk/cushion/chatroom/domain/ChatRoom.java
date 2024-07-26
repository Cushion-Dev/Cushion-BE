package com.chzzk.cushion.chatroom.domain;

import com.chzzk.cushion.global.common.BaseTimeEntity;
import com.chzzk.cushion.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotBlank
    String title;

    @NotBlank
    @Size(max = 15)
    private String partnerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Relationship partnerRel;

    private LocalDateTime lastUsedAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void updateLastUsedAt(LocalDateTime lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public void updateInfo(String partnerName, Relationship partnerRel) {
        this.partnerName = partnerName;
        this.partnerRel = partnerRel;
        this.title = partnerName + "(" + partnerRel + ")" + "님과의 쿠션";
    }
}
