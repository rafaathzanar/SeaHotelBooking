package com.zanar.hotelbooking.service.interf;

import com.zanar.hotelbooking.dto.LoginRequest;
import com.zanar.hotelbooking.dto.Response;
import com.zanar.hotelbooking.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}