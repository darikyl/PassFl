package com.project.passengerflow.repository;

import com.project.passengerflow.model.PassengerCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PassengerCoordinatesRepository extends JpaRepository<PassengerCoordinates, Integer> {
    List<PassengerCoordinates> findByDatePassengerAndTimePassengerAfter(LocalDate datePassenger, LocalTime timePassenger);

    List<PassengerCoordinates> findByBusStopPassenger_IdBusStopAndDatePassengerAndTimePassengerAfter(Integer busStopId, LocalDate datePassenger, LocalTime timePassenger);

}
