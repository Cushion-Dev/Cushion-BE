package com.chzzk.cushion.member.dto;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.chzzk.cushion.global.exception.ErrorCode.NOT_FOUND_MEMBER;

@Slf4j
@Data
@AllArgsConstructor
public class ApiMember {

    private String email;

    public Member toMember(MemberRepository memberRepository) {
        log.info("username={}", email);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CushionException(NOT_FOUND_MEMBER));
        log.info("Found member: {}", member);
        return member;
    }
}
