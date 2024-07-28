package com.chzzk.cushion.global.oauth2;

import com.chzzk.cushion.global.jwt.JwtTokenProvider;
import com.chzzk.cushion.global.oauth2.dto.CustomOAuth2User;
import com.chzzk.cushion.member.domain.Member;
import com.chzzk.cushion.member.domain.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createAccessCookie;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createCookie;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createMemberIdCookie;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createRealTestCookie;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie1;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie10;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie11;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie12;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie13;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie14;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie15;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie16;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie17;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie18;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie19;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie2;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie20;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie21;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie22;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie3;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie4;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie5;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie6;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie7;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie8;
import static com.chzzk.cushion.global.jwt.JwtTokenProvider.createTestCookie9;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${oauth2.success.redirect-url-new}")
    private String redirectUrlNewMember;

    @Value("${oauth2.success.redirect-url-existing}")
    private String redirectUrlExistingMember;

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Oauth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        boolean isNew = customUserDetails.isNew();

        Member member = memberRepository.findByUsername(username);
        String accessToken = jwtTokenProvider.createAccessToken(authentication, member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);


        response.addCookie(createCookie(refreshToken));
        response.addCookie(createAccessCookie(accessToken));
        response.addCookie(createMemberIdCookie(member.getId()));

        response.addCookie(createTestCookie1(member.getId()));
        response.addCookie(createTestCookie2(member.getId()));
        response.addCookie(createTestCookie3(member.getId()));
        response.addCookie(createTestCookie4(member.getId()));
        response.addCookie(createTestCookie5(member.getId()));
        response.addCookie(createTestCookie6(member.getId()));
        response.addCookie(createTestCookie7(member.getId()));
        response.addCookie(createTestCookie8(member.getId()));
        response.addCookie(createTestCookie9(member.getId()));
        response.addCookie(createTestCookie10(member.getId()));
        response.addCookie(createTestCookie11(member.getId()));
        response.addCookie(createTestCookie12(member.getId()));
        response.addCookie(createTestCookie13(member.getId()));
        response.addCookie(createTestCookie14(member.getId()));
        response.addCookie(createTestCookie15(member.getId()));
        response.addCookie(createTestCookie16(member.getId()));
        response.addCookie(createTestCookie17(member.getId()));
        response.addCookie(createTestCookie18(member.getId()));
        response.addCookie(createTestCookie19(member.getId()));
        response.addCookie(createTestCookie20(member.getId()));
        response.addCookie(createTestCookie21(member.getId()));
        response.addCookie(createTestCookie22(member.getId()));

        response.addCookie(createRealTestCookie(accessToken));

        if (isNew) {
            response.sendRedirect(redirectUrlNewMember);
        } else {
            response.sendRedirect(redirectUrlExistingMember + member.getId());
        }
    }
}
