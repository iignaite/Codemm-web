package com.csit321g1.nightingailles.codemm.user.dto;

import com.csit321g1.nightingailles.codemm.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Body for POST and PUT /api/users.
 * The password is required when creating a user. On update, leave it empty to keep the current password.
 */
public record UserRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email @Size(max = 150) String email,
        @Size(min = 8, max = 72) String password,
        @NotNull Role role) {
}
