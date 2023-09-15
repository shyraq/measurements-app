package com.example.gorbach.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @Size(min = 3, max = 30, message = "Name should be from 3 to 30")
    @NotEmpty(message = "Name should be not null")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
