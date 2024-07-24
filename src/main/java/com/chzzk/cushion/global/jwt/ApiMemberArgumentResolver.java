package com.chzzk.cushion.global.jwt;

import com.chzzk.cushion.global.exception.CushionException;
import com.chzzk.cushion.global.utils.AuthPrincipal;
import com.chzzk.cushion.member.dto.ApiMember;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.chzzk.cushion.global.exception.ErrorCode.EMPTY_JWT_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ApiMember.class) && parameter.hasParameterAnnotation(
                AuthPrincipal.class);
    }

    @Override
    public ApiMember resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                     NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = jwtTokenProvider.resolveToken(request);

        if (accessToken == null) {
            throw new CushionException(EMPTY_JWT_TOKEN);
        }

        String token = jwtTokenProvider.resolveToken(request);
        jwtTokenProvider.validateToken(token);
        String email = jwtTokenProvider.getPayload(token);
        log.debug("Resolved email from token: {}", email);

        return new ApiMember(email);
    }
}
