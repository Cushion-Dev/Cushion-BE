package com.chzzk.cushion.member.dto;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.chzzk.cushion.global.exception.ErrorCode.NOT_FOUND_MEMBER;

@Data
@AllArgsConstructor
public class ApiMember {

    private String username;

    public Member toMember(MemberRepository memberRepository) {
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new CushionException(NOT_FOUND_MEMBER));
    }
}
