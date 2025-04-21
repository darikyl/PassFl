package com.project.passengerflow.controller;

import com.project.passengerflow.dto.PathPartDTO;
import com.project.passengerflow.model.PathPart;
import com.project.passengerflow.repository.PathPartRepository;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/path_part")
public class PathPartController {

    @Autowired
    private PathPartRepository repository;

    @PostMapping
    public ResponseEntity<PathPartDTO> addPathPart(@RequestBody PathPartDTO pathPartDTO) {
        PathPart pathPart = new PathPart();
        pathPart.setIdPathPart(pathPartDTO.getIdPathPart());

        try {
            WKTReader wktReader = new WKTReader();
            Geometry geometry = wktReader.read(pathPartDTO.getPath());
            pathPart.setPath(geometry);
            Geometry textLabelGeometry = wktReader.read(pathPartDTO.getTextLabel());
            pathPart.setTextLabel(textLabelGeometry);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        pathPart.setIdBusStopStart(pathPartDTO.getIdBusStopStart());
        pathPart.setIdBusStopFinish(pathPartDTO.getIdBusStopFinish());

        PathPart savedPathPart = repository.save(pathPart);
        pathPartDTO.setIdPathPart(savedPathPart.getIdPathPart());
        return new ResponseEntity<>(pathPartDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PathPartDTO>> getAllPathParts() {
        List<PathPart> pathParts = repository.findAll();
        List<PathPartDTO> pathPartDTOList = new ArrayList<>();

        for (PathPart pathPart : pathParts) {
            PathPartDTO pathPartDTO = new PathPartDTO();
            pathPartDTO.setIdPathPart(pathPart.getIdPathPart());
            WKTWriter wktWriter = new WKTWriter();
            pathPartDTO.setPath(wktWriter.write(pathPart.getPath()));
            pathPartDTO.setIdBusStopStart(pathPart.getIdBusStopStart());
            pathPartDTO.setIdBusStopFinish(pathPart.getIdBusStopFinish());
            pathPartDTO.setTextLabel(wktWriter.write(pathPart.getTextLabel()));
            pathPartDTOList.add(pathPartDTO);
        }

        return new ResponseEntity<>(pathPartDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PathPartDTO> getPathPartById(@PathVariable Integer id) {
        Optional<PathPart> pathPartOptional = repository.findById(id);
        if (pathPartOptional.isPresent()) {
            PathPart pathPart = pathPartOptional.get();
            PathPartDTO pathPartDTO = new PathPartDTO();
            pathPartDTO.setIdPathPart(pathPart.getIdPathPart());
            WKTWriter wktWriter = new WKTWriter();
            pathPartDTO.setPath(wktWriter.write(pathPart.getPath()));
            pathPartDTO.setIdBusStopStart(pathPart.getIdBusStopStart());
            pathPartDTO.setIdBusStopFinish(pathPart.getIdBusStopFinish());
            pathPartDTO.setTextLabel(wktWriter.write(pathPart.getTextLabel()));
            return new ResponseEntity<>(pathPartDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
