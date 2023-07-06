package com.techhub.controller.rest;


import com.techhub.dto.reponse.UserResponseDto;
import com.techhub.dto.request.UserRegisterDto;
import com.techhub.service.SecurityService;
import com.techhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping({"/all"})
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        List<UserResponseDto> userResponseDtos = userService.getUsers();
        if (userResponseDtos.isEmpty()) {
            return new ResponseEntity<List<UserResponseDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        UserResponseDto userResponseDto = userService.getUserById(UUID.fromString(id));
        if (userResponseDto == null) {
            return new ResponseEntity<UserResponseDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody String keyWord,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        List<UserResponseDto> userResponseDtos = null;
        if (keyWord != null && !keyWord.isEmpty()) {
            userResponseDtos = userService.getUsersByFullName(keyWord);
            if (userResponseDtos.isEmpty()) {
                return new ResponseEntity<List<UserResponseDto>>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> create(@Valid @ModelAttribute UserRegisterDto registerDto
            , BindingResult bindingResult, HttpServletRequest request) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userService.save(registerDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
