package com.chzzk.cushion.member.application;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.global.exception.ErrorCode;
import com.chzzk.cushion.member.domain.CustomUserDetails;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CushionException(ErrorCode.NOT_FOUND_MEMBER));
        return new CustomUserDetails(member);
    }

    public CustomUserDetails loadUserByIdAndUsername(Long memberId, String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdAndEmail(memberId, email)
                .orElseThrow(() -> new CushionException(ErrorCode.NOT_FOUND_MEMBER));
        return new CustomUserDetails(member);
    }
}
