package ru.softtrack.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionCreateRequest {
    @NotBlank(message = "Login is required")
    String login;
    @NotBlank(message = "Password is required")
    String password;
}
