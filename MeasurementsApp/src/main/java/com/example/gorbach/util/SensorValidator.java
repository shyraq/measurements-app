package com.example.gorbach.util;

import com.example.gorbach.models.Sensor;
import com.example.gorbach.repositories.SensorRepository;
import com.example.gorbach.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    private final SensorRepository repository;

    @Autowired
    public SensorValidator(SensorService sensorService, SensorRepository repository) {
        this.sensorService = sensorService;
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if(repository.findByName(sensor.getName()).isPresent()){
            errors.rejectValue("name", "Sensor with this name is already exist");
        }
    }
}
