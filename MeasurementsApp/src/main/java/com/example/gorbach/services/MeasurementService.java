package com.example.gorbach.services;

import com.example.gorbach.models.Measurement;
import com.example.gorbach.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementsRepository repository;

    private final SensorService service;

    @Autowired
    public MeasurementService(MeasurementsRepository repository, SensorService service) {
        this.repository = repository;
        this.service = service;
    }

    @Transactional
    public void save(Measurement measurement) {
        enrich(measurement);
        repository.save(measurement);
    }

    private void enrich(Measurement measurement) {
        measurement.setSensor(service.findByName(measurement.getSensor().getName()).get());

        measurement.setAddedAt(LocalDateTime.now());
    }

    public List<Measurement> findAll() {
        return repository.findAll();
    }

    public Optional<Measurement> getOneById(int id) {
        return repository.findById(id);
    }
}
