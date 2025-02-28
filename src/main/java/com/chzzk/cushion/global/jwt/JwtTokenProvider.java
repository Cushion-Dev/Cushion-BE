package com.chzzk.cushion.global.jwt;

import static com.chzzk.cushion.global.exception.ErrorCode.INVALID_JWT_TOKEN;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.member.application.MyUserDetailService;
import com.chzzk.cushion.member.domain.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    @Value("${oauth2.success.redirect-url-new}")
    private static String redirectUrlNewMember;

    private final MyUserDetailService myUserDetailService;


    @Value("${oauth2.success.redirect-url-new}")
    private void setRedirectUrlNewMember(String redirectUrlNewMember) {
        JwtTokenProvider.redirectUrlNewMember = redirectUrlNewMember;
    }

    public static String getRedirectUrlNewMember() {
        return redirectUrlNewMember;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Access 토큰 생성
     */
    public String createAccessToken(Authentication authentication, Long memberId) {
        validateAuthentication(authentication);
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        claims.setSubject(authentication.getName());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }

    /**
     * Refresh 토큰 생성
     */
    public String createRefreshToken(Authentication authentication) {
        validateAuthentication(authentication);
        Claims claims = Jwts.claims();
        claims.setSubject(authentication.getName());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }

    public static Cookie createCookie(String refreshToken) {
        String cookieName = "refreshToken";
        Cookie cookie = new Cookie(cookieName, refreshToken);
        cookie.setHttpOnly(false);
        cookie.setSecure(true);
        cookie.setDomain("coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // accessToken 유효
        return cookie;
    }

    public static Cookie createAccessCookie(String accessToken) {
        String cookieName = "accessToken";
        Cookie cookie = new Cookie(cookieName, accessToken);
        cookie.setHttpOnly(false);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        if (getRedirectUrlNewMember().equals("http://localhost:8081")) {
            cookie.setSecure(false);
        } else {
            cookie.setDomain("coocian.com");
        }
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createMemberIdCookie(Long memberId) {
        String cookieName = "memberId";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    /*
    public static Cookie createTestCookie1(Long memberId) {
        String cookieName = "test1";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie2(Long memberId) {
        String cookieName = "test2";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie3(Long memberId) {
        String cookieName = "test3";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie4(Long memberId) {
        String cookieName = "test4";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie5(Long memberId) {
        String cookieName = "test5";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie6(Long memberId) {
        String cookieName = "test6";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie7(Long memberId) {
        String cookieName = "test7";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie8(Long memberId) {
        String cookieName = "test8";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie9(Long memberId) {
        String cookieName = "test9";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie10(Long memberId) {
        String cookieName = "test10";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie11(Long memberId) {
        String cookieName = "test11";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie12(Long memberId) {
        String cookieName = "test12";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(false);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie13(Long memberId) {
        String cookieName = "test13";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie14(Long memberId) {
        String cookieName = "test14";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("www.coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie15(Long memberId) {
        String cookieName = "test15";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    public static Cookie createTestCookie16(Long memberId) {
        String cookieName = "test16";
        Cookie cookie = new Cookie(cookieName, memberId.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // TODO : HTTPS 적용 시 적용 가능
        cookie.setDomain("coocian.com");
        cookie.setPath("/chat-list");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }
    */
    /*

    public static Cookie createRealTestCookie(String accessToken) {
        String cookieName = "accessToken2";
        Cookie cookie = new Cookie(cookieName, accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setDomain(".coocian.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 2400); // accessToken 유효
        return cookie;
    }

     */

    public Authentication getAuthenticationByToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        String userPrincipal = claims.getSubject();
        Long memberId = getMemberId(token);
        UserDetails userDetails = myUserDetailService.loadUserByIdAndUsername(memberId, userPrincipal);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getPayload(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {

            // 에러 로그 출력
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new CushionException(INVALID_JWT_TOKEN);
        }
    }

    private void validateAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication is required");
        }
    }
    public String reissueAccessToken(String refreshToken, Long memberId) {
        validateToken(refreshToken);
        Authentication authentication = getAuthenticationByToken(refreshToken);
        return createAccessToken(authentication, memberId);
    }

    public Long extractMemberId(String refreshToken) {
        Authentication authentication = getAuthenticationByToken(refreshToken);
        String name = authentication.getName();
        UserDetails userDetails = myUserDetailService.loadUserByUsername(name);
        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            return customUserDetails.getMemberId();
        }
        return null;
    }

    public Long getMemberId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claims.get("memberId", Long.class);
    }
}