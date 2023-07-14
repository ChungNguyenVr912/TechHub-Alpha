package com.techhub.controller.rest;


import com.techhub.dto.reponse.CommonResponseDto;
import com.techhub.dto.reponse.UserResponseDto;
import com.techhub.dto.request.UserRegisterDto;
import com.techhub.dto.request.UserUpdateRequestDto;
import com.techhub.service.SecurityService;
import com.techhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

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

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @ModelAttribute UserRegisterDto registerDto
            , BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userService.save(registerDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @GetMapping("/get-info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") final String token){
        UserResponseDto userResponseDto = userService.getUserInfo(token);
        if (userResponseDto == null){
            return new ResponseEntity<>(
                    new CommonResponseDto(false, "Cannot find user",null),HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(
                    new CommonResponseDto(true, "Success",userResponseDto),HttpStatus.OK);
        }
    }
    @PutMapping
    public ResponseEntity<?> create(@RequestHeader("Authorization") final String token
            ,@Valid @ModelAttribute UserUpdateRequestDto userUpdateRequestDto
            , BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userService.updateUser(userUpdateRequestDto, token);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
