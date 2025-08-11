package com.SwiftRide.SwiftRideApp.services.Impl;

import com.SwiftRide.SwiftRideApp.entities.WalletTransaction;
import com.SwiftRide.SwiftRideApp.repositories.WalletTransactionRepository;
import com.SwiftRide.SwiftRideApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }
}
