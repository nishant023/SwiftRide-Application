package com.SwiftRide.SwiftRideApp.repositories;


import com.SwiftRide.SwiftRideApp.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
}

