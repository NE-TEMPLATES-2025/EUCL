package com.paccy.eucl.services.impl;

import com.paccy.eucl.entities.User;
import com.paccy.eucl.exceptions.UnAuthorizedException;
import com.paccy.eucl.repository.IUserRepository;
import com.paccy.eucl.request.LoginRequest;
import com.paccy.eucl.response.AuthResponse;
import com.paccy.eucl.security.JwtService;
import com.paccy.eucl.security.UserDetailsServiceImpl;
import com.paccy.eucl.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                ()->new UnAuthorizedException("Invalid email or password")
        );

        boolean passwordMatch= passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if(!passwordMatch) {
            throw new UnAuthorizedException("Invalid email or password");
        }
//        Generate new token
        Map<String,Object> claims= new HashMap<>();
        claims.put("email",user.getEmail());

        UserDetails userDetails= userDetailsService.loadUserByUsername(user.getEmail());

        Authentication authentication= new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token= jwtService.generateToken(claims,userDetails);

        return new AuthResponse(token,user);
    }
}
