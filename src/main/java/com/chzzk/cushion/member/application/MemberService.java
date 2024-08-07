package com.chzzk.cushion.member.application;

import static com.chzzk.cushion.global.exception.ErrorCode.INVALID_JWT_TOKEN;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.global.jwt.JwtTokenProvider;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.member.dto.MemberDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        clearCookies(request, response);
    }

    @Transactional
    public void saveMemberAdditionalInfo(MemberDto memberDto, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);

        member.updateAdditionalInfo(memberDto.getAffiliation(), memberDto.getJob(), memberDto.getRealName());
    }

    @Transactional
    public void deleteMember(HttpServletRequest request, HttpServletResponse response, ApiMember apiMember) {
        Member member = apiMember.toMember(memberRepository);
        memberRepository.delete(member);

        clearCookies(request, response);
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("쿠키 이름: {}, 쿠키 값: {}", cookie.getName(), cookie.getValue());
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setDomain("coocian.com");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        } else {
            log.info("쿠키가 존재하지 않습니다.");
        }
    }

    public String reissueAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("refreshToken")) {
                String refreshToken = cookie.getValue();
                Long memberId = jwtTokenProvider.extractMemberId(refreshToken);
                jwtTokenProvider.validateToken(refreshToken);
                return jwtTokenProvider.reissueAccessToken(refreshToken, memberId);
            }
        }
        return null;
    }

    public boolean validateToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("refreshToken")) {
                String refreshToken = cookie.getValue();
                return jwtTokenProvider.validateToken(refreshToken);
            }
        }
        throw new CushionException(INVALID_JWT_TOKEN);
    }
}