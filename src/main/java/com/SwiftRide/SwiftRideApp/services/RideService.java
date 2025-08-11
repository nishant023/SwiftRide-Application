package com.SwiftRide.SwiftRideApp.services;

import com.SwiftRide.SwiftRideApp.entities.Driver;
import com.SwiftRide.SwiftRideApp.entities.Ride;
import com.SwiftRide.SwiftRideApp.entities.RideRequest;
import com.SwiftRide.SwiftRideApp.entities.Rider;
import com.SwiftRide.SwiftRideApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

//this is the core service, we will not allow any one to call anyone from outside
public interface RideService {

    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}

