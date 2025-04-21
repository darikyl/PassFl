package com.project.passengerflow.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "path_part")
public class PathPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_path_part", nullable = false)
    private Integer idPathPart;

    @Column(name = "path", columnDefinition = "geometry(LineString, 4326)", nullable = false)
    private Geometry path;

    @Column(name = "id_bus_stop_start", nullable = false)
    private Integer idBusStopStart;

    @Column(name = "id_bus_stop_finish", nullable = false)
    private Integer idBusStopFinish;

    @Column(name = "text_label", columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Geometry textLabel;

    // Getters and setters
    public Integer getIdPathPart() {
        return idPathPart;
    }

    public void setIdPathPart(Integer idPathPart) {
        this.idPathPart = idPathPart;
    }

    public Geometry getPath() {
        return path;
    }

    public void setPath(Geometry path) {
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

    public Geometry getTextLabel() {
        return textLabel;
    }

    public void setTextLabel(Geometry textLabel) {
        this.textLabel = textLabel;
    }
}
