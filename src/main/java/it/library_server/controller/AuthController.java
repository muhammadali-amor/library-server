package it.library_server.controller;

import it.library_server.entity.User;
import it.library_server.exception.ResourceNotFoundException;
import it.library_server.payload.ApiResponse;
import it.library_server.payload.LoginDto;
import it.library_server.payload.RegisterDto;
import it.library_server.repository.AuthRepository;
import it.library_server.service.AuthService;
import it.library_server.service.CustomOAuth2User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto request) {
        return authService.login(request, authenticationManager);
    }
    @GetMapping("/success")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Foydalanuvchi autentifikatsiya qilinmagan");
        }

        System.out.println("OAuth2User attributes: " + principal.getAttributes());

        return ResponseEntity.ok(principal.getAttributes()); // Barcha atributlarni JSON formatda qaytarish
    }


//    @PostMapping("/reset-password")
//    public HttpEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
//        ApiResponse<?> apiResponse = authService.resetPassword(resetPasswordDto);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
//    }

//    @GetMapping("/list")
//    public HttpEntity<?> getUser() {
//        List<RegisterDto> user = authService.getUser();
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("/register")
    public HttpEntity<?> addUser(@RequestBody RegisterDto registerDto) {
       return   authService.addUser(registerDto, authenticationManager);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id) {
        ApiResponse<?> apiResponse = authService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable UUID id, @RequestBody RegisterDto registerDto) {
        ApiResponse<?> apiResponse = authService.editUser(id, registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneUser(@PathVariable UUID id) {
        try {
            User user = authRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "get user", "id", id));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.ok(new ApiResponse<>("GetOneUserda xatolik", false));
        }
    }

//    @PostMapping("/send-message/{id}")
//    private HttpEntity<?> sendMessage(@PathVariable UUID id, @RequestBody MessageDto messageDto) {
//        ApiResponse<?> sendMessage = authService.sendMessage(id, messageDto);
//        return ResponseEntity.status(sendMessage.isSuccess() ? 200 : 409).body(sendMessage);
//    }
//
//    @GetMapping("/get-message/{id}")
//    private HttpEntity<?> getMessage(@PathVariable UUID id) {
//        List<MessageDto> messages = authService.getMessages(id);
//        return ResponseEntity.ok(messages);
//    }
//
//    @GetMapping("/get-admin-message")
//    private HttpEntity<?> getAdminMessage() {
//        List<MessageDto> adminMessages = authService.getAdminMessages();
//        return ResponseEntity.ok(adminMessages);
//    }
//
//    @PutMapping("/admin-reply-user/{messageId}")
//    private HttpEntity<?> adminReplyUser(@PathVariable UUID messageId, @RequestBody MessageDto messageDto) {
//        ApiResponse<?> replyUser = authService.adminReplyUser(messageId, messageDto);
//        return ResponseEntity.status(replyUser.isSuccess() ? 200 : 409).body(replyUser);
//    }
//
//    @DeleteMapping("/admin-reply-user/{messageId}")
//    private HttpEntity<?> adminReplyDelete(@PathVariable UUID messageId) {
//        ApiResponse<?> adminReplyDelete = authService.adminReplyDelete(messageId);
//        return ResponseEntity.status(adminReplyDelete.isSuccess() ? 200 : 409).body(adminReplyDelete);
//    }

}
