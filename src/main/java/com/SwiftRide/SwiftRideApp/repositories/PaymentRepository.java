package com.SwiftRide.SwiftRideApp.repositories;

import com.SwiftRide.SwiftRideApp.entities.Payment;
import com.SwiftRide.SwiftRideApp.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRide(Ride ride);
}