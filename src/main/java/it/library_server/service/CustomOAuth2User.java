package it.library_server.service;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User extends DefaultOAuth2User {

    private final String token;

    public CustomOAuth2User(OAuth2User delegate, String token) {
        super(delegate.getAuthorities(), delegate.getAttributes(), "email");
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
