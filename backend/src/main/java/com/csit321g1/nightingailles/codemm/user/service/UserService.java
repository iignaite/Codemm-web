package com.csit321g1.nightingailles.codemm.user.service;

import com.csit321g1.nightingailles.codemm.common.exception.ConflictException;
import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.user.dto.UserRequest;
import com.csit321g1.nightingailles.codemm.user.dto.UserResponse;
import com.csit321g1.nightingailles.codemm.user.entity.UserEntity;
import com.csit321g1.nightingailles.codemm.user.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(UserResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return UserResponse.from(findEntity(id));
    }

    public UserResponse create(UserRequest request) {
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("password is required when creating a user");
        }
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ConflictException("Email " + request.email() + " is already registered");
        }
        UserEntity user = new UserEntity();
        apply(user, request);
        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse update(Long id, UserRequest request) {
        UserEntity user = findEntity(id);
        if (userRepository.existsByEmailIgnoreCaseAndUserIdNot(request.email(), id)) {
            throw new ConflictException("Email " + request.email() + " is already registered");
        }
        apply(user, request);
        return UserResponse.from(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.delete(findEntity(id));
    }

    /** Shared lookup used by this service and by other modules that reference a user. */
    @Transactional(readOnly = true)
    public UserEntity findEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    private void apply(UserEntity user, UserRequest request) {
        user.setName(request.name().trim());
        user.setEmail(request.email().trim().toLowerCase());
        user.setRole(request.role());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }
    }
}
