package com.SwiftRide.SwiftRideApp.strategies.Impl;

import com.SwiftRide.SwiftRideApp.entities.Driver;
import com.SwiftRide.SwiftRideApp.entities.RideRequest;
import com.SwiftRide.SwiftRideApp.repositories.DriverRepository;
import com.SwiftRide.SwiftRideApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional()
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearbyTopRatedDrivers(rideRequest.getPickupLocation());
    }
}
