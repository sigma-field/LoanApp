package com.bb.loanapp.user;


import com.bb.loanapp.user.model.AuthResponse;
import com.bb.loanapp.user.model.SignInRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Operation(
            summary = "Authentication endpoint.",
            description = "Authenticate for the app by providing user details."
    )
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
