package com.chzzk.cushion.chatroom.domain;

import com.chzzk.cushion.global.common.BaseTimeEntity;
import com.chzzk.cushion.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotBlank
    @Size(max = 15)
    private String partnerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Relationship partnerRel;

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messages = new ArrayList<>();
}
