package com.chzzk.cushion.member.presentation;

import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.application.MemberService;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.member.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "member", description = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login/oauth2/naver")
    @Operation(summary = "소셜 로그인_네이버", description = "네이버 소셜 로그인을 위한 URL을 리다이렉트 합니다.")
    public RedirectView loginToNaver() {
        return new RedirectView("/oauth2/authorization/naver");
    }

    @GetMapping("/login/oauth2/kakao")
    @Operation(summary = "소셜 로그인_카카오", description = "카카오 소셜 로그인을 위한 URL을 리다이렉트 합니다.")
    public RedirectView loginToKakao() {
        return new RedirectView("/oauth2/authorization/kakao");
    }

    @GetMapping("/login/oauth2/google")
    @Operation(summary = "소셜 로그인_구글", description = "구글 소셜 로그인을 위한 URL을 리다이렉트 합니다.")
    public RedirectView loginToGoogle() {
        return new RedirectView("/oauth2/authorization/google");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "소셜 로그인한 계정을 로그아웃합니다.")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return memberService.logout(request, response, authentication);
    }

    @PostMapping("/my-info")
    public void saveAdditionalInfo(@RequestBody MemberDto memberDto, @AuthPrincipal ApiMember apiMember) {
        memberService.saveMemberAdditionalInfo(memberDto, apiMember);
    }
}
