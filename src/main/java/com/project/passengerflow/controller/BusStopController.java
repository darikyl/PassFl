package com.project.passengerflow.controller;

import com.project.passengerflow.dto.BusStopDTO;
import com.project.passengerflow.model.BusStop;
import com.project.passengerflow.repository.BusStopRepository;
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
@RequestMapping("/api/bus_stop")
public class BusStopController {

    @Autowired
    private BusStopRepository repository;

    @PostMapping
    public ResponseEntity<BusStopDTO> addBusStop(@RequestBody BusStopDTO busStopDTO) {
        BusStop busStop = new BusStop();
        busStop.setNameBusStop(busStopDTO.getNameBusStop());
        busStop.setIdBusStop(busStopDTO.getIdBusStop());
        try {
            WKTReader wktReader = new WKTReader();
            Geometry geometry = wktReader.read(busStopDTO.getCoordinatesBusStop());
            busStop.setCoordinatesBusStop(geometry);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BusStop savedBusStop = repository.save(busStop);
        busStopDTO.setIdBusStop(savedBusStop.getIdBusStop());
        return new ResponseEntity<>(busStopDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BusStopDTO>> getAllBusStops() {
        List<BusStop> busStops = repository.findAll();
        List<BusStopDTO> busStopDTOList = new ArrayList<>();

        for (BusStop busStop : busStops) {
            BusStopDTO busStopDTO = new BusStopDTO();
            busStopDTO.setIdBusStop(busStop.getIdBusStop());
            WKTWriter wktWriter = new WKTWriter();
            busStopDTO.setCoordinatesBusStop(wktWriter.write(busStop.getCoordinatesBusStop()));
            busStopDTO.setNameBusStop(busStop.getNameBusStop());
            busStopDTOList.add(busStopDTO);
        }

        return new ResponseEntity<>(busStopDTOList, HttpStatus.OK);
    }

}
