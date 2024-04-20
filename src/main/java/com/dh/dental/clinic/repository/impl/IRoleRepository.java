package com.dh.dental.clinic.repository.impl;

import com.dh.dental.clinic.entity.Role;
import com.dh.dental.clinic.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
