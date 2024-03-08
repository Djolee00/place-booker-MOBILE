package com.placebooker.controller;

import com.placebooker.domain.Role;
import com.placebooker.domain.User;
import com.placebooker.dto.UserPatchRequest;
import com.placebooker.service.RoleService;
import com.placebooker.service.UserService;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.ADMIN + "\")")
    public void updateUsersRoles(
            @PathVariable Long id, @RequestBody @Valid UserPatchRequest userPatchRequest) {
        User user = userService.getUserById(id);
        Set<Role> roles =
                userPatchRequest.roles().stream()
                        .map(r -> roleService.getRoleByCode(r.code()))
                        .collect(Collectors.toSet());
        userService.updateUserRoles(user, roles);
    }
}
