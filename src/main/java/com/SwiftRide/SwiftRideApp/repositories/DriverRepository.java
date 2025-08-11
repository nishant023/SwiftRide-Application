package com.SwiftRide.SwiftRideApp.repositories;


import com.SwiftRide.SwiftRideApp.entities.Driver;
import com.SwiftRide.SwiftRideApp.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
//    @Query(value = """
//        SELECT d.*, ST_Distance_Sphere(d.current_location, ST_GeomFromText(:pickupLocation, 4326)) AS distance
//        FROM driver d
//        WHERE d.available = TRUE
//        AND ST_Distance_Sphere(d.current_location, ST_GeomFromText(:pickupLocation, 4326)) <= 10000
//        ORDER BY distance
//        LIMIT 10
//        """, nativeQuery = true)
//    List<Driver> findMatchingDrivers(@Param("pickupLocation") String pickupLocation); // e.g., 'POINT(77.5946 12.9716)'


    // ✅ Native query using ST_Distance_Sphere, passing Point directly
    @Query(value = """
            SELECT d.*
            FROM driver d
            WHERE d.available = TRUE
            AND ST_Distance_Sphere(d.current_location, :pickupLocation) <= 10000
            ORDER BY ST_Distance_Sphere(d.current_location, :pickupLocation)
            LIMIT 10
            """, nativeQuery = true)
    List<Driver> findTenNearestDrivers(@Param("pickupLocation") Point pickupLocation);

    //this is optimized query because the current
    // location is stored in Geo spatial data base is stored by the use of the indexes and inside this if
    // you query something then you dont have to go to all the data first it will go to the index table
    // from there it will find the index page so query time is log n, so that how data base creates the
    // indexes of the pick up location

    //There will be stratgy manager becuase there are lots of strategy so it will manage them


    // ✅ Top-rated drivers nearby
    @Query(value = """
            SELECT d.*
            FROM driver d
            WHERE d.available = TRUE
            AND ST_Distance_Sphere(d.current_location, :pickupLocation) <= 15000
            ORDER BY d.rating DESC
            LIMIT 10
            """, nativeQuery = true)
    List<Driver> findTenNearbyTopRatedDrivers(@Param("pickupLocation") Point pickupLocation);

    // ✅ Simple JPA query
    Optional<Driver> findByUser(User user);
}
