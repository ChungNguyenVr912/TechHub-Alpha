package com.techhub.service;

public interface SecurityService {
    boolean isAuthenticated();
    boolean isValidToken(String token);
}
