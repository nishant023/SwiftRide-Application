package com.SwiftRide.SwiftRideApp.strategies;

import com.SwiftRide.SwiftRideApp.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);

}