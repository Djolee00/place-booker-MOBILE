package com.placebooker.service.impl;

import com.placebooker.domain.Role;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.repository.RoleRepository;
import com.placebooker.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByCode(Role.RoleCode code) {
        return roleRepository
                .findByName(code)
                .orElseThrow(
                        () -> new NotFoundException("Role with code " + code + " doesn't exist"));
    }
}
