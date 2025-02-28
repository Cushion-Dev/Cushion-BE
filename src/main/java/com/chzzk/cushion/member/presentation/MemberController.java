package com.chzzk.cushion.member.presentation;

import com.chzzk.cushion.global.jwt.JwtTokenProvider;
import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.application.MemberService;
import com.chzzk.cushion.member.dto.ApiMember;
import com.chzzk.cushion.member.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "member", description = "회원 API")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

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
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        memberService.logout(request, response, authentication);
    }

    @PostMapping("/my-info")
    @Operation(summary = "사용자 추가 정보 설정", description = "사용자의 추가 정보(소속, 직무, 이름)을 설정합니다.")
    public void saveAdditionalInfo(@Valid @RequestBody MemberDto memberDto,
                                   @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        memberService.saveMemberAdditionalInfo(memberDto, apiMember);
    }

    @PutMapping("/my-info")
    @Operation(summary = "사용자 추가 정보 수정", description = "사용자의 추가 정보(소속, 직무, 이름)을 수정합니다.")
    public void updateAdditionalInfo(@Valid @RequestBody MemberDto memberDto,
                                     @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        memberService.saveMemberAdditionalInfo(memberDto, apiMember);
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    public void deleteMember(HttpServletRequest request, HttpServletResponse response, @Parameter(hidden = true) @AuthPrincipal ApiMember apiMember) {
        memberService.deleteMember(request, response, apiMember);

    }

    @GetMapping("/validate-token")
    @Operation(summary = "refresh 토큰 유효성 검증", description = "refresh 토큰의 유효성을 검증합니다.")
    public boolean validateToken(HttpServletRequest request) {
        return memberService.validateToken(request);
    }

    @GetMapping("/reissue")
    @Operation(summary = "액세스 토큰 재발급", description = "만료된 액세스 토큰을 재발급 받는다.")
    public String reissueAccessToken(HttpServletRequest request) {
        return memberService.reissueAccessToken(request);
    }
}
