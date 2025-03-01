package it.library_server.config;

import it.library_server.entity.User;
import it.library_server.repository.AuthRepository;
import it.library_server.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;

    public OAuth2LoginSuccessHandler(JwtTokenProvider jwtTokenProvider, AuthRepository authRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authRepository = authRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response,
            jakarta.servlet.FilterChain chain,
            Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<User> userOptional = authRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String token = jwtTokenProvider.generateAccessToken(userOptional.get());
            response.sendRedirect("/loginSuccess?token=" + token);
        } else {
            response.sendRedirect("/loginFailure");
        }
    }
}
