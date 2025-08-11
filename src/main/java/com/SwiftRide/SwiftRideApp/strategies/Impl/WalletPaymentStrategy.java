package com.SwiftRide.SwiftRideApp.strategies.Impl;


//Rider had 232, Driver had 500
//Ride cost is 100, commission = 30
//Rider -> 232-100 = 132
//Driver -> 500 + (100 - 30) = 570

import com.SwiftRide.SwiftRideApp.entities.Driver;
import com.SwiftRide.SwiftRideApp.entities.Payment;
import com.SwiftRide.SwiftRideApp.entities.Rider;
import com.SwiftRide.SwiftRideApp.entities.enums.PaymentStatus;
import com.SwiftRide.SwiftRideApp.entities.enums.TransactionMethod;
import com.SwiftRide.SwiftRideApp.repositories.PaymentRepository;
import com.SwiftRide.SwiftRideApp.services.WalletService;
import com.SwiftRide.SwiftRideApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),
                driversCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
