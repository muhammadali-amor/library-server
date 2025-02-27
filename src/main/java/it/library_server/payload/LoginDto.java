package it.library_server.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private UUID userId;
    @NotBlank(message = "Email bo'lishi shart")
    private String email;
    private String password;
}
