package com.example.foodcloud.controller.core.point.req;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class PointReq {
    @Min(0)
    @Max(3000000)
    private final int point;

    public PointReq(int point) {
        this.point = point;
    }
}
