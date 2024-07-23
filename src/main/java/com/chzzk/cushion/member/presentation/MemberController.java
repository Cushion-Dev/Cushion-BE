package com.chzzk.cushion.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "member", description = "회원 API")
public class MemberController {

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
}
