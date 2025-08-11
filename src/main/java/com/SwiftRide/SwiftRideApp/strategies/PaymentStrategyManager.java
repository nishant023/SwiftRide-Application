package com.SwiftRide.SwiftRideApp.strategies;


import com.SwiftRide.SwiftRideApp.entities.enums.PaymentMethod;
import com.SwiftRide.SwiftRideApp.strategies.Impl.CashPaymentStrategy;
import com.SwiftRide.SwiftRideApp.strategies.Impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
