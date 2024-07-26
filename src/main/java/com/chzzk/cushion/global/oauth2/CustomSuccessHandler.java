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

        // 만약 이미 존재하는 회원이라면 redirectUrl = localhost:3000/chat-list
        // 만약 처음 가입하는 회원이라면 redirectUrl = localhost:3000/cushion
        // 페이지가 열리면 사용자 추가 정보 POST 보냄 -> 채팅방 만들기 상대방 정보 받음 -> 채팅방 생성 누름 -> 방금 만든 채팅방으로 이동

        if (isNew) {
            response.sendRedirect(redirectUrlNewMember);
        } else {
            response.sendRedirect(redirectUrlExistingMember + member.getId());
        }
    }
}
