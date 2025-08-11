package com.SwiftRide.SwiftRideApp.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {
    double calculateDistance(Point src, Point dest);
}
