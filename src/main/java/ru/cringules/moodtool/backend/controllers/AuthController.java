package ru.cringules.moodtool.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.cringules.moodtool.backend.data.User;
import ru.cringules.moodtool.backend.data.repositories.UserRepository;
import ru.cringules.moodtool.backend.domain.dto.AuthCheckResponse;
import ru.cringules.moodtool.backend.domain.dto.AuthenticationRequestDto;
import ru.cringules.moodtool.backend.domain.dto.AuthenticationResponseDto;
import ru.cringules.moodtool.backend.services.TokenService;
import ru.cringules.moodtool.backend.util.ConflictException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.getToken(authentication.getName());

        return ResponseEntity.ok(new AuthenticationResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody AuthenticationRequestDto request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new ConflictException("Username " + request.getUsername() + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.getToken(authentication.getName());

        return ResponseEntity.ok(new AuthenticationResponseDto(token));
    }

    @GetMapping("check")
    public ResponseEntity<AuthCheckResponse> check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthCheckResponse(user.getUsername()));
    }
}
