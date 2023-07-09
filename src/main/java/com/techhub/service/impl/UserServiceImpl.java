package com.techhub.service.impl;


import com.techhub.dto.reponse.UserResponseDto;
import com.techhub.dto.request.UserRegisterDto;
import com.techhub.entity.Role;
import com.techhub.entity.User;
import com.techhub.repository.RoleRepository;
import com.techhub.repository.UserRepository;
import com.techhub.service.ImageService;
import com.techhub.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository
            , RoleRepository roleRepository
            , ModelMapper modelMapper
            , ImageService imageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.imageService = imageService;
    }

    @Override
    public Iterable<UserResponseDto> findAll() {
        Iterable<User> entities = userRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDto> findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return Optional.ofNullable(modelMapper.map(user, UserResponseDto.class));
    }

    @Override
    public void remove(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserResponseDto userResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(user, userResponseDto);
        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> getUsersByFullName(String fullName) {
        List<User> users = userRepository.findByFullNameLike(fullName);
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public boolean save(UserRegisterDto userRegisterDto) throws IOException {
        String hashedPassword = BCrypt.hashpw(userRegisterDto.getPassword(), BCrypt.gensalt(10));
        userRegisterDto.setPassword(hashedPassword);
        MultipartFile avatar = userRegisterDto.getAvatar();
        String filename;
        String imgUrl = null;
        if (avatar != null) {
            filename = imageService.save(avatar);
            imgUrl = imageService.getImageUrl(filename);
        }
        Role role = roleRepository.findById(3L).orElse(Role.builder().id(4L).roleName("OTHER").build());
        User user = User.builder()
                .username(userRegisterDto.getUsername())
                .email(userRegisterDto.getEmail())
                .password(hashedPassword)
                .avatar(imgUrl)
                .roles(new HashSet<>())
                .build();
        user.getRoles().add(role);
        userRepository.save(user);
        return true;
    }
}