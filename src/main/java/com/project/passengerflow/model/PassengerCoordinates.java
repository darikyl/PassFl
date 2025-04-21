package com.project.passengerflow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "passenger_coordinates")
public class PassengerCoordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passenger", nullable = false)
    private Integer idPassenger;

    @Column(name = "coordinates_passenger", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Geometry coordinatesPassenger;

    @Column(name = "date_passenger", nullable = false)
    private LocalDate datePassenger;

    @Column(name = "time_passenger", nullable = false)
    private LocalTime timePassenger;

    @Column(name = "passenger_phone", nullable = true)
    private String passengerPhone;

    @ManyToOne
    @JoinColumn(name = "bus_stop_passenger", referencedColumnName = "id_bus_stop")
    @JsonBackReference
    private BusStop busStopPassenger;

     @ManyToOne
    @JoinColumn(name = "path_part_passenger", referencedColumnName = "id_path_part")
    @JsonBackReference
     private PathPart pathPartPassenger;

    // Getters and setters
    public Integer getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(Integer idPassenger) {
        this.idPassenger = idPassenger;
    }

    public Geometry getCoordinatesPassenger() {
        return coordinatesPassenger;
    }

    public void setCoordinatesPassenger(Geometry coordinatesPassenger) {
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

    public BusStop getBusStopPassenger() {
        return busStopPassenger;
    }

    public void setBusStopPassenger(BusStop busStopPassenger) {
        this.busStopPassenger = busStopPassenger;
    }

    public PathPart getPathPartPassenger() {
        return pathPartPassenger;
    }

    public void setPathPartPassenger(PathPart pathPartPassenger) {
        this.pathPartPassenger = pathPartPassenger;
    }
}
