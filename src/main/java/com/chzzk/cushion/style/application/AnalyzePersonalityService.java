package com.chzzk.cushion.style.application;

import com.chzzk.cushion.chatroom.domain.ChatRoom;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.style.domain.AnalyzePersonalityRequestDataGenerator;
import com.chzzk.cushion.style.domain.ClovaStudioApiExecutor;
import com.chzzk.cushion.style.dto.AnalyzePersonalityRequest;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalyzePersonalityService {

    private final MemberRepository memberRepository;
    private final AnalyzePersonalityRequestDataGenerator clovaApiRequestDataGenerator;
    private final ClovaStudioApiExecutor clovaStudioApiExecutor;

    @Transactional
    public void analyzePersonality(ApiMember apiMember,
                                   AnalyzePersonalityRequest analyzePersonalityRequest) {
        Member member = apiMember.toMember(memberRepository);
        ChatRoom chatRoom = member.findChatRoomById(analyzePersonalityRequest.getRoomId());

        JSONObject requestData = clovaApiRequestDataGenerator
                .generateWithConversation(member, analyzePersonalityRequest.getConversation(), chatRoom);
        String personality = clovaStudioApiExecutor.analyzePersonality(requestData);

        chatRoom.updatePersonality(personality);
    }
}
