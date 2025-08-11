package com.SwiftRide.SwiftRideApp.services;

import com.SwiftRide.SwiftRideApp.dto.DriverDto;
import com.SwiftRide.SwiftRideApp.dto.SignupDto;
import com.SwiftRide.SwiftRideApp.dto.UserDto;

public interface AuthService {
    String[] login(String email,String password);
    UserDto signUp(SignupDto signUpDto);
    DriverDto onboardNewDriver(String userId);

    DriverDto onboardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
