package com.placebooker.service;

import com.placebooker.domain.Role;

public interface RoleService {

    Role getRoleByCode(Role.RoleCode code);
}
