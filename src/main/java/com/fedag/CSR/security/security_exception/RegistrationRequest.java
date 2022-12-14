package com.fedag.CSR.security.security_exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "Регистрация пользователя", description = "Запрос на регистрацию пользователя")
public class RegistrationRequest {
    @Schema(description = "Почтовый адрес пользователя",
            maxLength = 255,
            minLength = 1,
            example = "some@mail.com")
    @NotBlank
    @Size(max = 255)
    private String email;

    @Schema(description = "Пароль пользователя",
            maxLength = 255,
            minLength = 1,
            example = "bcryptqwerty")
    @NotBlank
    @Size(max = 255)
    private String password;

    @Schema(description = "Имя пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someName")
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Schema(description = "Фамилия пользователя",
            maxLength = 255,
            minLength = 1,
            example = "someSurname")
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @Schema(description = "Ссылка steam",
            maxLength = 255,
            minLength = 1,
            example = "steamcommunity.com/")
    @NotBlank
    @Size(max = 255)
    private String steamLink;

    @Schema(description = "Логин",
            maxLength = 20,
            minLength = 6,
            example = "qwerty")
    @NotBlank
    @Size(max = 255)
    private String userName;
}
