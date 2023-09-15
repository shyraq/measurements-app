package com.example.gorbach.controllers;

import com.example.gorbach.dto.MeasurementDTO;
import com.example.gorbach.dto.MeasurementResponse;
import com.example.gorbach.models.Measurement;
import com.example.gorbach.services.MeasurementService;
import com.example.gorbach.util.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.gorbach.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;

    private final ModelMapper modelMapper;

    private final MeasurementValidator measurementValidator;


    @Autowired
    public MeasurementsController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                      BindingResult result) {

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, result);
        if (result.hasErrors()) {
            returnErrorsToClient(result);
        }

        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @GetMapping
    public MeasurementResponse getMeasurements() {
        return new MeasurementResponse(measurementService.findAll().stream()
                .map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @GetMapping("/raining")
    public Long getRainingDays(){
        return measurementService.findAll()
                .stream().filter(Measurement::isRaining).count();
    }
}


