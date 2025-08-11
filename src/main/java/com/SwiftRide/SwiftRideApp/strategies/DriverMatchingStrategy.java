package com.SwiftRide.SwiftRideApp.strategies;

import com.SwiftRide.SwiftRideApp.entities.Driver;
import com.SwiftRide.SwiftRideApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
