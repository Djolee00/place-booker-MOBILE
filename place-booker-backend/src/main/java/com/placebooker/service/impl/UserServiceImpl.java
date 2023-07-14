package com.placebooker.service.impl;

import com.placebooker.domain.User;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.repository.UserRepository;
import com.placebooker.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("User with ID: " + id + " can't be found"));
  }
}
