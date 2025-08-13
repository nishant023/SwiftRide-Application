package com.SwiftRide.SwiftRideApp.services.Impl;


import com.SwiftRide.SwiftRideApp.entities.Driver;
import com.SwiftRide.SwiftRideApp.entities.Ride;
import com.SwiftRide.SwiftRideApp.entities.RideRequest;
import com.SwiftRide.SwiftRideApp.entities.Rider;
import com.SwiftRide.SwiftRideApp.entities.enums.RideRequestStatus;
import com.SwiftRide.SwiftRideApp.entities.enums.RideStatus;
import com.SwiftRide.SwiftRideApp.exceptions.ResourceNotFoundException;
import com.SwiftRide.SwiftRideApp.repositories.RideRepository;
import com.SwiftRide.SwiftRideApp.repositories.RideRequestRepository;
import com.SwiftRide.SwiftRideApp.services.RideRequestService;
import com.SwiftRide.SwiftRideApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + rideId));
    }


    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        rideRequestRepository.save(rideRequest);

        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);

//        Ride ride1 = new Ride();
//        ride.setDriver(driver);
//        ride.setRider(rideRequest.getRider());
////        ride.setR(rideRequest);
//        ride.setRideStatus(RideStatus.CONFIRMED);
//        ride.setCreatedTime(LocalDateTime.now());
//        ride = rideRepository.save(ride);
//
//        rideRequest.se(ride);
//        rideRequestRepository.save(rr);

//        return ride;
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
