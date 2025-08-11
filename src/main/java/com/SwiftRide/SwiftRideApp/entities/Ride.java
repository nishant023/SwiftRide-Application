package com.SwiftRide.SwiftRideApp.entities;

import com.SwiftRide.SwiftRideApp.entities.enums.PaymentMethod;
import com.SwiftRide.SwiftRideApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_ride_rider", columnList = "rider_id"),
        @Index(name = "idx_ride_driver", columnList = "driver_id")
})
public class Ride {
    //this has the otp which is passed to the rider and once driver riches the location, rider has to
    // provide the otp and then driver will start the ride


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "POINT SRID 4326")
    private Point pickupLocation;

    @Column(columnDefinition = "POINT SRID 4326")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
