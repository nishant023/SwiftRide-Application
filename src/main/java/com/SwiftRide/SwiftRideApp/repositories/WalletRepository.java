package com.SwiftRide.SwiftRideApp.repositories;

import com.SwiftRide.SwiftRideApp.entities.User;
import com.SwiftRide.SwiftRideApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}