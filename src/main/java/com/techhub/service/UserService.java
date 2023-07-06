package com.techhub.service;


import com.techhub.dto.reponse.UserResponseDto;
import com.techhub.dto.request.UserRegisterDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> getUsers();
    List<UserResponseDto> getUsersByFullName(String fullName);
    UserResponseDto getUserById(UUID id);
    Iterable<UserResponseDto> findAll();
    Optional<UserResponseDto> findById(UUID id);
    boolean save(UserRegisterDto user) throws IOException;
    void remove(UUID id);
}
