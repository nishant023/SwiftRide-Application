package com.SwiftRide.SwiftRideApp.services.Impl;


import com.SwiftRide.SwiftRideApp.dto.DriverDto;
import com.SwiftRide.SwiftRideApp.dto.RideDto;
import com.SwiftRide.SwiftRideApp.dto.RideRequestDto;
import com.SwiftRide.SwiftRideApp.dto.RiderDto;
import com.SwiftRide.SwiftRideApp.entities.*;
import com.SwiftRide.SwiftRideApp.entities.enums.RideRequestStatus;
import com.SwiftRide.SwiftRideApp.entities.enums.RideStatus;
import com.SwiftRide.SwiftRideApp.exceptions.ResourceNotFoundException;
import com.SwiftRide.SwiftRideApp.repositories.RideRequestRepository;
import com.SwiftRide.SwiftRideApp.repositories.RiderRepository;
import com.SwiftRide.SwiftRideApp.services.DriverService;
import com.SwiftRide.SwiftRideApp.services.RatingService;
import com.SwiftRide.SwiftRideApp.services.RideService;
import com.SwiftRide.SwiftRideApp.services.RiderService;
import com.SwiftRide.SwiftRideApp.strategies.RideStrategyManager;
import com.SwiftRide.SwiftRideApp.utils.GeometryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        // ✅ Step 1: Get authenticated Rider
        Rider currentRider = getCurrentRider();

//        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);

        // ✅ Step 2: Manually construct RideRequest
        RideRequest rideRequest = new RideRequest();
        rideRequest.setPickupLocation(GeometryUtil.createPoint(rideRequestDto.getPickupLocation()));
        rideRequest.setDropOffLocation(GeometryUtil.createPoint(rideRequestDto.getDropOffLocation()));
        rideRequest.setPaymentMethod(rideRequestDto.getPaymentMethod());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRequestedTime(LocalDateTime.now());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(currentRider);// Only set once

        // ✅ Step 3: Calculate fare
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        // ✅ Step 4: Save ride request
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        // ✅ Step 5: Match drivers (broadcast to nearby drivers)
        List<Driver> drivers = rideStrategyManager
                .driverMatchingStrategy(currentRider.getRating())
                .findMatchingDriver(rideRequest);

//        TODO : Send notification to all the drivers about this ride request
        // ✅ Step 6: Return DTO (works because MapperConfig handles Point & Rider→UserDto mapping)
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException(("Rider does not own this ride with id: "+rideId));
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if((rider.getId())!=(ride.getRider().getId())){
            throw new RuntimeException("Rider is not the owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with user with id: "+user.getId()
        ));
    }

}
