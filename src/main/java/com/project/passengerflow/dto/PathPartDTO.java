package com.project.passengerflow.dto;

public class PathPartDTO {
    private Integer idPathPart;
    private String path;
    private Integer idBusStopStart;
    private Integer idBusStopFinish;
    private String textLabel;

    // Getters and setters
    public Integer getIdPathPart() {
        return idPathPart;
    }

    public void setIdPathPart(Integer idPathPart) {
        this.idPathPart = idPathPart;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIdBusStopStart() {
        return idBusStopStart;
    }

    public void setIdBusStopStart(Integer idBusStopStart) {
        this.idBusStopStart = idBusStopStart;
    }

    public Integer getIdBusStopFinish() {
        return idBusStopFinish;
    }

    public void setIdBusStopFinish(Integer idBusStopFinish) {
        this.idBusStopFinish = idBusStopFinish;
    }

    public String getTextLabel() {
        return textLabel;
    }

    public void setTextLabel(String textLabel) {
        this.textLabel = textLabel;
    }
}
