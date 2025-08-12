package com.SwiftRide.SwiftRideApp.utils;

import com.SwiftRide.SwiftRideApp.dto.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;


public class GeometryUtil {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point createPoint(PointDto pointDto) {
        if (pointDto == null || pointDto.getCoordinates() == null || pointDto.getCoordinates().length < 2) {
            throw new IllegalArgumentException("Invalid PointDto: coordinates are missing");
        }

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        double longitude = pointDto.getCoordinates()[0];
        double latitude = pointDto.getCoordinates()[1];

        return geometryFactory.createPoint(new Coordinate(longitude, latitude));

//    private static final GeometryFactory geometryFactory = new GeometryFactory();
//    public static Point createPoint(PointDto pointDto) {
//        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
//        Coordinate coordinate = new Coordinate(pointDto.getCoordinates()[0],
//                pointDto.getCoordinates()[1]
//        );
//        return geometryFactory.createPoint(coordinate);
    }
}
