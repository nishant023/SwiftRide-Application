package com.SwiftRide.SwiftRideApp.services;


import com.SwiftRide.SwiftRideApp.entities.Payment;
import com.SwiftRide.SwiftRideApp.entities.Ride;
import com.SwiftRide.SwiftRideApp.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
