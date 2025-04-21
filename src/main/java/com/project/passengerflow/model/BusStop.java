package com.project.passengerflow.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "bus_stop")
public class BusStop {
    @Id
    @Column(name = "id_bus_stop", nullable = false)
    private Integer idBusStop;

    @Column(name = "coordinates_bus_stop", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Geometry coordinatesBusStop;

    @Column(name = "name_bus_stop", nullable = false)
    private String nameBusStop;

    // Getters and setters
    public Integer getIdBusStop() {
        return idBusStop;
    }

    public void setIdBusStop(Integer idBusStop) {
        this.idBusStop = idBusStop;
    }

    public Geometry getCoordinatesBusStop() {
        return coordinatesBusStop;
    }

    public void setCoordinatesBusStop(Geometry coordinatesBusStop) {
        this.coordinatesBusStop = coordinatesBusStop;
    }

    public String getNameBusStop() {
        return nameBusStop;
    }

    public void setNameBusStop(String nameBusStop) {
        this.nameBusStop = nameBusStop;
    }
}
