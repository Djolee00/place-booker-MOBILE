package com.placebooker.service.impl;

import com.placebooker.domain.Role;
import com.placebooker.domain.User;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.repository.UserRepository;
import com.placebooker.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetailsService userDetailsService() {
        return username ->
                userRepository
                        .findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User with ID: " + id + " can't be found"));
    }

    @Override
    @Transactional
    public void updateUserRoles(User user, Set<Role> roles) {
        user.setRoles(roles);
        userRepository.save(user);
    }
}
