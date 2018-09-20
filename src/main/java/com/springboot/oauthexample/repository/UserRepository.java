package com.springboot.oauthexample.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.oauthexample.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByUsername(String username);

    Optional<User> findById(Long id);


	public void save(org.springframework.security.core.userdetails.User user);
}
