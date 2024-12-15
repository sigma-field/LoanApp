package com.bb.loanapp.user;


import com.bb.loanapp.security.JwtManager;
import com.bb.loanapp.user.model.AuthResponse;
import com.bb.loanapp.user.model.SignInRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtManager jwtManager;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(SignInRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userService.loadUserByUsername(request.getEmail());

        var jwtToken = jwtManager.generateToken(new HashMap<>(), user);

        return new AuthResponse(jwtToken);
    }

}
