package com.placebooker.mapper;

import com.placebooker.domain.User;
import com.placebooker.dto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
  public static User toEntity(UserDto userDto) {
    return User.builder()
        .id(userDto.id())
        .email(userDto.email())
        .password(userDto.password())
        .age(userDto.age())
        .firstName(userDto.firstName())
        .lastName(userDto.lastName())
        .build();
  }

  public static UserDto toDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .lastName(user.getLastName())
        .firstName(user.getFirstName())
        .age(user.getAge())
        .build();
  }
}
