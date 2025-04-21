package com.project.passengerflow.repository;

import com.project.passengerflow.model.BusStop;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusStopRepository extends JpaRepository<BusStop, Integer> {
    Optional<BusStop> findByCoordinatesBusStop(Point coordinates);

    @Query(value = "SELECT * FROM public.bus_stop b WHERE ST_DWithin(b.coordinates_bus_stop, :passengerLocation, :radiusMeters)", nativeQuery = true)
    List<BusStop> findWithinDistance(@Param("passengerLocation") Geometry passengerLocation, @Param("radiusMeters") double radiusMeters);


}
