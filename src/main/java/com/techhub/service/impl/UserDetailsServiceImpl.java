package com.techhub.service.impl;

import com.techhub.entity.Role;
import com.techhub.entity.User;
import com.techhub.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + "was not found in database!");
        }

        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role: roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            grantedAuthorities.add(authority);
        }

        return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        grantedAuthorities);
    }
}
