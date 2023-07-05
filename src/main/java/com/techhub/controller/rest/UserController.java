package com.techhub.controller.rest;


import com.techhub.dto.UserDto;
import com.techhub.dto.UserRegisterDto;
import com.techhub.service.SecurityService;
import com.techhub.service.UserService;
import org.springframework.http.HttpRequest;
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
import org.springframework.web.bind.annotation.RequestPart;
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
        List<UserDto> userDtos = userService.getUsers();
        if (userDtos.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        UserDto userDto = userService.getUserById(UUID.fromString(id));
        if (userDto == null) {
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody String keyWord,
                                    @RequestHeader("Authorization") final String authToken) {
        if (!securityService.isAuthenticated() && !securityService.isValidToken(authToken)) {
            return new ResponseEntity<>("Responding with unauthorized error. Message - {}", HttpStatus.UNAUTHORIZED);
        }
        List<UserDto> userDtos = null;
        if (keyWord != null && !keyWord.isEmpty()) {
            userDtos = userService.getUsersByFullName(keyWord);
            if (userDtos.isEmpty()) {
                return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
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
