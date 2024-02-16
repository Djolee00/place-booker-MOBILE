package com.placebooker.service;

import com.placebooker.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User getUserById(Long id);
}
