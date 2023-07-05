package com.techhub.service;


import com.techhub.dto.UserDto;
import com.techhub.dto.UserRegisterDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserDto> getUsers();
    List<UserDto> getUsersByFullName(String fullName);
    UserDto getUserById(UUID id);
    Iterable<UserDto> findAll();
    Optional<UserDto> findById(UUID id);
    boolean save(UserRegisterDto user) throws IOException;
    void remove(UUID id);
}
