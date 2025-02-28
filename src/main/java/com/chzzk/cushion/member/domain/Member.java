package com.chzzk.cushion.member.domain;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.global.common.BaseTimeEntity;
import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.global.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Email(regexp = "^[a-zA-Z0-9._%+-]{1,20}@[a-zA-Z0-9.-]{1,10}\\.[a-zA-Z]{2,}$")
    private String email;

    @NotBlank
    private String password;

    private String username;

    private String affiliation;

    private String job;

    private String realName;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    public ChatRoom findChatRoomById(long roomId) {
        return chatRooms.stream()
                .filter(chatRoom -> chatRoom.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new CushionException(ErrorCode.NOT_FOUND_CHAT_ROOM_THAT_MEMBER));
    }

    public void updateAdditionalInfo(String affiliation, String job, String realName) {
        this.affiliation = affiliation;
        this.job = job;
        this.realName = realName;
    }
}
