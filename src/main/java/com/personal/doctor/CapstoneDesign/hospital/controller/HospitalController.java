package com.personal.doctor.CapstoneDesign.hospital.controller;

import com.personal.doctor.CapstoneDesign.hospital.controller.dto.HospitalListResponseDto;
import com.personal.doctor.CapstoneDesign.hospital.controller.dto.HospitalResponseDto;
import com.personal.doctor.CapstoneDesign.hospital.service.HospitalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping(value = "/hospital/name", produces = "application/json;charset=UTF-8")
    public List<HospitalListResponseDto> withName(@RequestBody String type) {
        return hospitalService.findHospitalType(type);
    }

    @PostMapping(value = "/hospital/address", produces = "application/json;charset=UTF-8")
    public List<HospitalListResponseDto> withAddress(@RequestBody HospitalResponseDto responseDto) {
        return hospitalService.findHospitalAddress(responseDto);
    }

}