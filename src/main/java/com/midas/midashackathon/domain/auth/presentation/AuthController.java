package com.midas.midashackathon.domain.auth.presentation;

import com.midas.midashackathon.domain.admin.presentation.dto.response.UserResponse;
import com.midas.midashackathon.domain.user.presentation.dto.request.SignInRequest;
import com.midas.midashackathon.domain.user.presentation.dto.request.SignUpRequest;
import com.midas.midashackathon.domain.user.presentation.dto.response.SignInResponse;
import com.midas.midashackathon.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        authService.signUp(request);
    }

    @PostMapping("/sign-in")
    public SignInResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }

    @GetMapping("/user-info")
    public UserResponse getUserInfo() {
        return authService.getUserInfo();
    }
}
