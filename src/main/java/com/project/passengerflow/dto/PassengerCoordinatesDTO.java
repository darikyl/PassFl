package com.project.passengerflow.dto;

import com.project.passengerflow.model.PathPart;

import java.time.LocalDate;
import java.time.LocalTime;

public class PassengerCoordinatesDTO {
    private Integer idPassenger;
    private String coordinatesPassenger;
    private LocalDate datePassenger;
    private LocalTime timePassenger;
    private String passengerPhone;
    private Integer busStopPassengerId;
    private Integer pathPartPassengerId;

    public Integer getPathPartPassengerId() {
        return pathPartPassengerId;
    }

    public void setPathPartPassengerId(Integer pathPartPassenger) {
        this.pathPartPassengerId = pathPartPassenger;
    }


    // Getters and setters
    public Integer getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(Integer idPassenger) {
        this.idPassenger = idPassenger;
    }

    public String getCoordinatesPassenger() {
        return coordinatesPassenger;
    }

    public void setCoordinatesPassenger(String coordinatesPassenger) {
        this.coordinatesPassenger = coordinatesPassenger;
    }

    public LocalDate getDatePassenger() {
        return datePassenger;
    }

    public void setDatePassenger(LocalDate datePassenger) {
        this.datePassenger = datePassenger;
    }

    public LocalTime getTimePassenger() {
        return timePassenger;
    }

    public void setTimePassenger(LocalTime timePassenger) {
        this.timePassenger = timePassenger;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public Integer getBusStopPassengerId() {
        return busStopPassengerId;
    }

    public void setBusStopPassengerId(Integer busStopPassengerId) {
        this.busStopPassengerId = busStopPassengerId;
    }
}
