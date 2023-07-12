package com.techhub.controller.rest;

import com.techhub.dto.reponse.LoginResponse;
import com.techhub.dto.reponse.RefreshTokenResponse;
import com.techhub.dto.request.LoginRequest;
import com.techhub.security.JwtTokenProvider;
import com.techhub.service.SecurityService;
import com.techhub.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final SecurityService securityService;
    private final JwtTokenProvider tokenProvider;

    public AuthController(UserService userService, SecurityService securityService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.securityService = securityService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            loginResponse = userService.authLogin(loginRequest);
            loginResponse.setMessage("Đăng nhập thành công!");

            Cookie httpOnlyCookie = new Cookie("accessToken", loginResponse.getToken());
            httpOnlyCookie.setHttpOnly(true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, httpOnlyCookie.toString());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
            loginResponse.setMessage("Đăng nhập thất bại!");
            return new ResponseEntity<>(loginResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") final String refreshToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(refreshToken)) {
            return new ResponseEntity<>(
                    new RefreshTokenResponse("Invalid refresh token or expired!", null)
                    , HttpStatus.UNAUTHORIZED);
        } else {
            String accessToken = tokenProvider.generateTokenFromRefreshToken(refreshToken);
            return new ResponseEntity<>(
                    new RefreshTokenResponse("success!", accessToken)
                    , HttpStatus.ACCEPTED);

        }
    }
}
