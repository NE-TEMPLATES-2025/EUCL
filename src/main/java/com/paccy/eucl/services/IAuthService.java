package com.paccy.eucl.services;

import com.paccy.eucl.request.LoginRequest;
import com.paccy.eucl.response.AuthResponse;

public interface IAuthService {

    AuthResponse login(LoginRequest loginRequest);
}
