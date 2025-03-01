package it.library_server.service;

import it.library_server.entity.User;
import it.library_server.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthervice extends DefaultOAuth2UserService {
    private final AuthRepository authRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Foydalanuvchi ma’lumotlarini olish
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Foydalanuvchini bazada tekshirish
        Optional<User> userDetails = authRepository.findByEmail(email);

        User user;
        if (userDetails.isPresent()) {
            user = userDetails.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setAuthProvider("GOOGLE");
            authRepository.save(user);
        }

        return oAuth2User;
    }
}
