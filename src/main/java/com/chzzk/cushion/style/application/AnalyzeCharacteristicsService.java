package com.chzzk.cushion.style.application;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.domain.ClovaApiRequestDataGenerator;
import com.chzzk.cushion.style.domain.ClovaOcrApiExecutor;
import com.chzzk.cushion.style.domain.ClovaStudioApiExecutor;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzeCharacteristicsService {

    private final MemberRepository memberRepository;
    private final ClovaOcrApiExecutor clovaOcrApiExecutor;
    private final ClovaApiRequestDataGenerator clovaApiRequestDataGenerator;
    private final ClovaStudioApiExecutor clovaStudioApiExecutor;

    public String analyzeCharacteristics(ApiMember apiMember, long roomId, List<MultipartFile> multipartFiles) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(roomId);

        String conversation = clovaOcrApiExecutor.execute(multipartFiles);

        JSONObject requestData = clovaApiRequestDataGenerator
                .generateWithConversation(member, conversation, chatRoom);
        return clovaStudioApiExecutor.analyzeCharacteristics(requestData);
    }
}
