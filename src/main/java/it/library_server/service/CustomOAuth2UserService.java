package it.library_server.service;

import it.library_server.entity.Role;
import it.library_server.entity.User;
import it.library_server.entity.enums.RoleName;
import it.library_server.repository.AuthRepository;
import it.library_server.repository.RoleRepository;
import it.library_server.security.JwtTokenProvider;
import it.library_server.service.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AuthRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        // ✅ USER roli bazada bor yoki yo‘qligini tekshiramiz
        Role userRole = roleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("USER role not found in DB"));

        // ✅ Foydalanuvchini bazadan topamiz yoki yaratamiz
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .name(oAuth2User.getAttribute("given_name")) // Ismni olish
                            .surname(oAuth2User.getAttribute("family_name")) // Familiyani olish
                            .roles(Set.of(userRole))  // ✅ Role entity qo‘shamiz
                            .authProvider("GOOGLE")
                            .enabled(true)
                            .build();
                    return userRepository.save(newUser);
                });

        // ✅ JWT token generatsiya qilish
        String token = jwtTokenProvider.generateAccessToken(user);

        return new CustomOAuth2User(oAuth2User, token);
    }
}
