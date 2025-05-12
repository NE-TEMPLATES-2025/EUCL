package com.paccy.eucl.services;


import com.paccy.eucl.entities.User;
import com.paccy.eucl.request.RegisterRequest;

public interface IUserService {

    User createCustomer(RegisterRequest registerRequest);
    User createAdmin(RegisterRequest registerRequest);
}
