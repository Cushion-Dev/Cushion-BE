package com.chzzk.cushion.global.oauth2.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final Oauth2Dto oauth2Dto;
    private final boolean isNew;

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public String getUsername() {
        return oauth2Dto.getUsername();
    }

    @Override
    public String getName() {
        return oauth2Dto.getEmail();
    }

    public boolean isNew() {
        return isNew;
    }
}
