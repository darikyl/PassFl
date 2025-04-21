package com.project.passengerflow.controller;

import com.project.passengerflow.dto.PassengerCoordinatesDTO;
import com.project.passengerflow.model.BusStop;
import com.project.passengerflow.model.PassengerCoordinates;
import com.project.passengerflow.model.PathPart;
import com.project.passengerflow.repository.BusStopRepository;
import com.project.passengerflow.repository.PassengerCoordinatesRepository;
import com.project.passengerflow.repository.PathPartRepository;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passenger_coordinates")
public class PassengerCoordinatesController {

    @Autowired
    private PassengerCoordinatesRepository repository;

    @Autowired
    private BusStopRepository busStopRepository;

    @Autowired
    private PathPartRepository pathPartRepository;

    private Optional<BusStop> findNearestBusStop(Geometry passengerLocation, double radiusMeters) {
        List<BusStop> nearbyBusStops = busStopRepository.findWithinDistance(passengerLocation, radiusMeters);
        if (!nearbyBusStops.isEmpty()) {
            return Optional.of(nearbyBusStops.get(0));
        }
        return Optional.empty();
    }


    @PostMapping
    public ResponseEntity<PassengerCoordinatesDTO> addPassengerCoordinates(@RequestBody PassengerCoordinatesDTO passengerDTO) {
        PassengerCoordinates passengerCoordinates = new PassengerCoordinates();
        passengerCoordinates.setDatePassenger(passengerDTO.getDatePassenger());
        passengerCoordinates.setTimePassenger(passengerDTO.getTimePassenger());
        passengerCoordinates.setPassengerPhone(passengerDTO.getPassengerPhone());


        try {
            WKTReader wktReader = new WKTReader();
            Geometry passengerLocation = wktReader.read(passengerDTO.getCoordinatesPassenger());
            passengerCoordinates.setCoordinatesPassenger(passengerLocation);

            // Знайти найближчу автобусну зупинку в радіусі 4 метрів
            Optional<BusStop> nearestBusStop = findNearestBusStop(passengerLocation, 0.00004);
            nearestBusStop.ifPresent(passengerCoordinates::setBusStopPassenger);


        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PassengerCoordinates savedPassengerCoordinates = repository.save(passengerCoordinates);
        passengerDTO.setIdPassenger(savedPassengerCoordinates.getIdPassenger());

        // Додатково оновити дані зупинки, якщо вони знайдені
        if (passengerCoordinates.getBusStopPassenger() != null) {
            passengerDTO.setBusStopPassengerId(passengerCoordinates.getBusStopPassenger().getIdBusStop());
        }
//
//          if (passengerCoordinates.getPathPartPassenger() != null) {
//            passengerDTO.setPathPartPassengerId(passengerCoordinates.getPathPartPassenger().getIdPathPart());
//        }
        return new ResponseEntity<>(passengerDTO, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<PassengerCoordinatesDTO>> getAllCoordinates() {
        List<PassengerCoordinates> passengerCoordinatesList = repository.findAll();
        List<PassengerCoordinatesDTO> passengerCoordinatesDTOList = new ArrayList<>();

        for (PassengerCoordinates coordinates : passengerCoordinatesList) {
            PassengerCoordinatesDTO passengerDTO = new PassengerCoordinatesDTO();
            passengerDTO.setIdPassenger(coordinates.getIdPassenger());
            WKTWriter wktWriter = new WKTWriter();
            passengerDTO.setCoordinatesPassenger(wktWriter.write(coordinates.getCoordinatesPassenger()));
            passengerDTO.setDatePassenger(coordinates.getDatePassenger());
            passengerDTO.setTimePassenger(coordinates.getTimePassenger());
            passengerDTO.setPassengerPhone(coordinates.getPassengerPhone());
            if (coordinates.getBusStopPassenger() != null) {
                passengerDTO.setBusStopPassengerId(coordinates.getBusStopPassenger().getIdBusStop());
            }
            if (coordinates.getPathPartPassenger() != null) {
                passengerDTO.setPathPartPassengerId(coordinates.getPathPartPassenger().getIdPathPart());
            }
            passengerCoordinatesDTOList.add(passengerDTO);
        }

        return new ResponseEntity<>(passengerCoordinatesDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<PassengerCoordinates> getCoordinatesById(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @GetMapping("/last_30_seconds")
    public ResponseEntity<List<PassengerCoordinatesDTO>> getCoordinatesLast30Seconds() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        LocalTime thirtySecondsAgo = nowTime.minusSeconds(30);
        List<PassengerCoordinates> passengerCoordinatesList = repository.findByDatePassengerAndTimePassengerAfter(nowDate, thirtySecondsAgo);

        List<PassengerCoordinatesDTO> passengerDTOList = new ArrayList<>();
        for (PassengerCoordinates coordinates : passengerCoordinatesList) {
            PassengerCoordinatesDTO passengerDTO = new PassengerCoordinatesDTO();
            passengerDTO.setIdPassenger(coordinates.getIdPassenger());
            passengerDTO.setCoordinatesPassenger(coordinates.getCoordinatesPassenger().toText());
            passengerDTO.setDatePassenger(coordinates.getDatePassenger());
            passengerDTO.setTimePassenger(coordinates.getTimePassenger());
            passengerDTO.setPassengerPhone(coordinates.getPassengerPhone());
            if (coordinates.getBusStopPassenger() != null) {
                passengerDTO.setBusStopPassengerId(coordinates.getBusStopPassenger().getIdBusStop());
            }
             if (coordinates.getPathPartPassenger() != null) {
                passengerDTO.setPathPartPassengerId(coordinates.getPathPartPassenger().getIdPathPart());
            }
            passengerDTOList.add(passengerDTO);
        }

        return new ResponseEntity<>(passengerDTOList, HttpStatus.OK);
    }

    @GetMapping("/bus_stop/{busStopId}")
    public ResponseEntity<List<PassengerCoordinatesDTO>> getPassengerCoordinatesByBusStopIdAndTime(@PathVariable Integer busStopId) {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        LocalTime thirtySecondsAgo = nowTime.minusSeconds(30);

        List<PassengerCoordinates> passengerCoordinatesList;
        passengerCoordinatesList = repository.findByBusStopPassenger_IdBusStopAndDatePassengerAndTimePassengerAfter(busStopId, nowDate, thirtySecondsAgo);


        List<PassengerCoordinatesDTO> passengerDTOList = new ArrayList<>();
        for (PassengerCoordinates coordinates : passengerCoordinatesList) {
            PassengerCoordinatesDTO passengerDTO = new PassengerCoordinatesDTO();
            passengerDTO.setIdPassenger(coordinates.getIdPassenger());
            passengerDTO.setCoordinatesPassenger(coordinates.getCoordinatesPassenger().toText());
            passengerDTO.setDatePassenger(coordinates.getDatePassenger());
            passengerDTO.setTimePassenger(coordinates.getTimePassenger());
            passengerDTO.setPassengerPhone(coordinates.getPassengerPhone());
            if (coordinates.getBusStopPassenger() != null) {
                passengerDTO.setBusStopPassengerId(coordinates.getBusStopPassenger().getIdBusStop());
            }
            passengerDTOList.add(passengerDTO);
        }

        return new ResponseEntity<>(passengerDTOList, HttpStatus.OK);
    }

//
//    @PostMapping
//    public ResponseEntity<PassengerCoordinatesDTO> addPassengerCoordinates(@RequestBody PassengerCoordinatesDTO passengerDTO) {
//        PassengerCoordinates passengerCoordinates = new PassengerCoordinates();
//        passengerCoordinates.setDatePassenger(passengerDTO.getDatePassenger());
//        passengerCoordinates.setTimePassenger(passengerDTO.getTimePassenger());
//        passengerCoordinates.setPassengerPhone(passengerDTO.getPassengerPhone());
//
//        try {
//            WKTReader wktReader = new WKTReader();
//            Geometry geometry = wktReader.read(passengerDTO.getCoordinatesPassenger());
//            passengerCoordinates.setCoordinatesPassenger(geometry);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        if (passengerDTO.getBusStopPassengerId() != null) {
//            Optional<BusStop> busStop = busStopRepository.findById(passengerDTO.getBusStopPassengerId());
//            busStop.ifPresent(passengerCoordinates::setBusStopPassenger);
//        }
//
//        PassengerCoordinates savedPassengerCoordinates = repository.save(passengerCoordinates);
//        passengerDTO.setIdPassenger(savedPassengerCoordinates.getIdPassenger());
//
//        return new ResponseEntity<>(passengerDTO, HttpStatus.CREATED);
//    }

    @GetMapping("/getList")
    public ResponseEntity<List<PassengerCoordinatesDTO>> getPassengerCoordinatesList() {
        List<PassengerCoordinates> passengerCoordinates = repository.findAll();
        List<PassengerCoordinatesDTO> passengerDTOList = new ArrayList<>();

        for (PassengerCoordinates coordinates : passengerCoordinates) {
            PassengerCoordinatesDTO passengerDTO = new PassengerCoordinatesDTO();
            passengerDTO.setIdPassenger(coordinates.getIdPassenger());
            passengerDTO.setCoordinatesPassenger(coordinates.getCoordinatesPassenger().toText());
            passengerDTO.setDatePassenger(coordinates.getDatePassenger());
            passengerDTO.setTimePassenger(coordinates.getTimePassenger());
            passengerDTO.setPassengerPhone(coordinates.getPassengerPhone());
            if (coordinates.getBusStopPassenger() != null) {
                passengerDTO.setBusStopPassengerId(coordinates.getBusStopPassenger().getIdBusStop());
            }
            passengerDTOList.add(passengerDTO);
        }

        return new ResponseEntity<>(passengerDTOList, HttpStatus.OK);
    }

     @PostMapping("/addPassengers")
    public ResponseEntity<?> addPassengersCoordinates(@RequestBody List<PassengerCoordinatesDTO> passengerDTOList) {
        List<PassengerCoordinatesDTO> savedPassengerDTOs = new ArrayList<>();

        for (PassengerCoordinatesDTO passengerDTO : passengerDTOList) {
            PassengerCoordinates passengerCoordinates = new PassengerCoordinates();
            passengerCoordinates.setDatePassenger(passengerDTO.getDatePassenger());
            passengerCoordinates.setTimePassenger(passengerDTO.getTimePassenger());
            passengerCoordinates.setPassengerPhone(passengerDTO.getPassengerPhone());

            try {
                WKTReader wktReader = new WKTReader();
                Geometry passengerLocation = wktReader.read(passengerDTO.getCoordinatesPassenger());
                passengerCoordinates.setCoordinatesPassenger(passengerLocation);

                Optional<BusStop> nearestBusStop = findNearestBusStop(passengerLocation, 0.00004);
                nearestBusStop.ifPresent(passengerCoordinates::setBusStopPassenger);

                if (passengerDTO.getPathPartPassengerId() != null) {
                    Optional<PathPart> pathPart = pathPartRepository.findById(passengerDTO.getPathPartPassengerId());
                    pathPart.ifPresent(passengerCoordinates::setPathPartPassenger);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            PassengerCoordinates savedPassengerCoordinates = repository.save(passengerCoordinates);
            passengerDTO.setIdPassenger(savedPassengerCoordinates.getIdPassenger());

            if (passengerCoordinates.getBusStopPassenger() != null) {
                passengerDTO.setBusStopPassengerId(passengerCoordinates.getBusStopPassenger().getIdBusStop());
            }
            if (passengerCoordinates.getPathPartPassenger() != null) {
                passengerDTO.setPathPartPassengerId(passengerCoordinates.getPathPartPassenger().getIdPathPart());
            }

            savedPassengerDTOs.add(passengerDTO);
        }

        return new ResponseEntity<>(savedPassengerDTOs, HttpStatus.CREATED);
    }

}
