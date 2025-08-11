package com.SwiftRide.SwiftRideApp.services;

import com.SwiftRide.SwiftRideApp.dto.DriverDto;
import com.SwiftRide.SwiftRideApp.dto.RideDto;
import com.SwiftRide.SwiftRideApp.dto.RideRequestDto;
import com.SwiftRide.SwiftRideApp.dto.RiderDto;
import com.SwiftRide.SwiftRideApp.entities.Rider;
import com.SwiftRide.SwiftRideApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}

