package com.csit321g1.nightingailles.codemm.user.repository;

import com.csit321g1.nightingailles.codemm.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndUserIdNot(String email, Long userId);
}
