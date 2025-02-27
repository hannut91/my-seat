package com.codesoom.myseat.repositories;

import com.codesoom.myseat.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository 
        extends JpaRepository<Role, Long> {
    List<Role> findAllByEmail(String email);

    Role save(Role role);
}
