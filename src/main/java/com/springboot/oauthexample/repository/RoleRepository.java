package com.springboot.oauthexample.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.oauthexample.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByName(String roleName);
    
}