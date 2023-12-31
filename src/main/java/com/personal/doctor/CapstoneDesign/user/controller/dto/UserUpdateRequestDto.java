package com.personal.doctor.CapstoneDesign.user.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {
    private String userName;
    private String location;

    @Builder
    public UserUpdateRequestDto(String userName, String location) {
        this.userName = userName;
        this.location = location;
    }
}
