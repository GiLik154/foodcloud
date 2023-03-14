package com.example.foodcloud.controller.core.point.dto;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class PointDto {
    @Min(0)
    @Max(3000000)
    private final int point;

    public PointDto(int point) {
        this.point = point;
    }
}
