package com.SwiftRide.SwiftRideApp.services;

import com.SwiftRide.SwiftRideApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
