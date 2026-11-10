package com.csit321g1.nightingailles.codemm.user.dto;

import com.csit321g1.nightingailles.codemm.user.entity.Role;
import com.csit321g1.nightingailles.codemm.user.entity.UserEntity;
import java.time.Instant;

/** What the API returns for a user. The password hash is never included. */
public record UserResponse(Long userId, String name, String email, Role role, Instant createdAt) {

    public static UserResponse from(UserEntity user) {
        return new UserResponse(user.getUserId(), user.getName(), user.getEmail(), user.getRole(),
                user.getCreatedAt());
    }
}
