package com.techhub.controller.rest;


import com.techhub.dto.reponse.UserResponseDto;
import com.techhub.dto.request.UserRegisterDto;
import com.techhub.service.SecurityService;
import com.techhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    private final SecurityService securityService;

    public UserRestController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> create(@Valid @ModelAttribute UserRegisterDto registerDto
            , BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userService.save(registerDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
