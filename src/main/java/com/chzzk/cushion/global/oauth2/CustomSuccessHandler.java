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

        if (isNew) {
            response.sendRedirect(redirectUrlNewMember);
        } else {
            response.sendRedirect(redirectUrlExistingMember + member.getId());
        }
    }
}
