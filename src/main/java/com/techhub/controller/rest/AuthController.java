package com.techhub.controller.rest;

import com.techhub.dto.reponse.LoginResponse;
import com.techhub.dto.reponse.UserResponseDto;
import com.techhub.dto.request.LoginRequest;
import com.techhub.security.JwtTokenProvider;
import com.techhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager
            , PasswordEncoder passwordEncoder
            , UserService userService, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);
            UserResponseDto userResponseDto = userService.findByUsername(loginRequest.getUsername());
//            Cookie cookie = new Cookie("accessToken", token);
//            cookie.setHttpOnly(true);
//            response.addCookie(cookie);
            return new ResponseEntity<>(new LoginResponse("Đăng nhập thành công!", token,userResponseDto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new LoginResponse("Đăng nhập thất bại!", null,null), HttpStatus.BAD_REQUEST);
        }
    }
}
