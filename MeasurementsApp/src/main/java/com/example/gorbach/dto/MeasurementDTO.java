package com.example.gorbach.dto;

import com.example.gorbach.models.Sensor;
import jakarta.validation.constraints.*;

public class MeasurementDTO {

    @Min(value = -100)
    @Max(value = 100)
    @NotNull
    private double value;

    @NotNull(message = "Raining should not be null")
    private Boolean raining;

    @NotNull(message = "Sensor should not be empty")
    private SensorDTO sensor;


    public MeasurementDTO(Double value, Boolean raining, SensorDTO sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public MeasurementDTO() {
    }


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
