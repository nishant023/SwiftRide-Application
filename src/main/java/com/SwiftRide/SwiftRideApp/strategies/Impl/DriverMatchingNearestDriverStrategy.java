package com.SwiftRide.SwiftRideApp.strategies.Impl;

import com.SwiftRide.SwiftRideApp.entities.Driver;
import com.SwiftRide.SwiftRideApp.entities.RideRequest;
import com.SwiftRide.SwiftRideApp.repositories.DriverRepository;
import com.SwiftRide.SwiftRideApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }
}
