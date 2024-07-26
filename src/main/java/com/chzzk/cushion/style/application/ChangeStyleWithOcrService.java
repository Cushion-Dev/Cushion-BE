package com.chzzk.cushion.style.application;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
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
    private final MemberRepository memberRepository;

    public String changeStyleWithOcr(ApiMember apiMember, long roomId, List<MultipartFile> multipartFiles) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(roomId);

        String extractedConversation = clovaOcrApiExecutor.execute(multipartFiles);
        return extractedConversation;
    }
}
