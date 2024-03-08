package com.placebooker.service;

import com.placebooker.domain.Role;
import com.placebooker.domain.User;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User getUserById(Long id);

    void updateUserRoles(User user, Set<Role> roles);
}
