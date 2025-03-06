package it.library_server.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.library_server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ApiResponse<T> implements Serializable {
    private String message;
    private boolean success;
    private String resToken;
    private RegisterDto registerDto;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, String resToken, RegisterDto registerDto) {
        this.message = message;
        this.success = success;
        this.resToken = resToken;
        this.registerDto = registerDto;
    }
}