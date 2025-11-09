package com.doguni.web.dto;

import com.doguni.model.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password ; // exactly 6 characters, must include only digits, not empty/blank text

    @NotNull
    private UserRole role ; // must be valid House enum value, not null
}
