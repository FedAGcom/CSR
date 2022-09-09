package com.fedag.CSR.email;

import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.enums.Role;
import com.fedag.CSR.exception.InvalidConfirmationTokenException;
import com.fedag.CSR.exception.ObjectNotFoundException;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/activate")
@RequiredArgsConstructor
public class EmailController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Operation(summary = "Активация учетной записи пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Учетная запись активирована успешно",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @GetMapping("/confirm")
    public ResponseEntity<?> activateUser(@RequestParam("token") String token) {
        User user = userRepository.findUserByConfirmationToken(token).orElseThrow(() ->
                new ObjectNotFoundException("User doesn't exist."));
        if (token == null) {
            throw new InvalidConfirmationTokenException("Invalid token");
        } else if (user.getConfirmationToken().equals(token)) {
            UserUpdate userUpdate = new UserUpdate(user.getId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUserName(),
                    user.getPassword(),
                    Role.user,
                    user.getCreated(),
                    user.getSteamLink(),
                    user.getConfirmationToken(),
                    true);
            userService.update(userUpdate);
            ResponseEntity.ok("Account verified successfully!");
        }
        return ResponseEntity.ok("Something went wrong.");
    }
}
