package com.paccy.eucl.services.impl;

import com.paccy.eucl.entities.User;
import com.paccy.eucl.enums.ERole;
import com.paccy.eucl.exceptions.BadRequestException;
import com.paccy.eucl.exceptions.UnAuthorizedException;
import com.paccy.eucl.repository.IUserRepository;
import com.paccy.eucl.request.RegisterRequest;
import com.paccy.eucl.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createCustomer(RegisterRequest registerRequest) {
        Optional<User> existingUser= userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) throw new BadRequestException("User already exists");

        User user= User
                .builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .nationalId(registerRequest.getNationalId())
                .role(ERole.CUSTOMER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User createAdmin(RegisterRequest registerRequest) {
        Optional<User> existingUser= userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) throw new BadRequestException("User already exists");

        User user= User
                .builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .nationalId(registerRequest.getNationalId())
                .role(ERole.ADMIN)
                .build();
        return userRepository.save(user);
    }
}
