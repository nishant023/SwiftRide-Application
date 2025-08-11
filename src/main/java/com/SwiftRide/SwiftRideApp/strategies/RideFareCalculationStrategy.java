package com.SwiftRide.SwiftRideApp.strategies;


import com.SwiftRide.SwiftRideApp.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequest rideRequest);

}

