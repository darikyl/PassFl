package com.project.passengerflow.dto;

public class BusStopDTO {
    private Integer idBusStop;
    private String coordinatesBusStop;
    private String nameBusStop;

    // Getters and setters
    public Integer getIdBusStop() {
        return idBusStop;
    }

    public void setIdBusStop(Integer idBusStop) {
        this.idBusStop = idBusStop;
    }

    public String getCoordinatesBusStop() {
        return coordinatesBusStop;
    }

    public void setCoordinatesBusStop(String coordinatesBusStop) {
        this.coordinatesBusStop = coordinatesBusStop;
    }

    public String getNameBusStop() {
        return nameBusStop;
    }

    public void setNameBusStop(String nameBusStop) {
        this.nameBusStop = nameBusStop;
    }
}
