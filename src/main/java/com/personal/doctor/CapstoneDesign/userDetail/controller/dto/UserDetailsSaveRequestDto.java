package com.personal.doctor.CapstoneDesign.userDetail.controller.dto;

import com.personal.doctor.CapstoneDesign.user.domain.Users;
import com.personal.doctor.CapstoneDesign.userDetail.domain.UserDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDetailsSaveRequestDto {

    private String age;
    private String gender;
    private String disease1;
    private String disease2;
    private String disease3;
    private String surgery;
    private String activity1;
    private String activity2;
    private String activity3;
    private Users users;

    public UserDetailsSaveRequestDto(String age, String gender, String disease1, String disease2, String disease3,
                                     String surgery, String activity1, String activity2, String activity3) {
        this.age = age;
        this.gender = gender;
        this.disease1 = disease1;
        this.disease2 = disease2;
        this.disease3 = disease3;
        this.surgery = surgery;
        this.activity1 = activity1;
        this.activity2 = activity2;
        this.activity3 = activity3;
    }

    public UserDetails toEntity(UserDetails entity) {
        return UserDetails.builder()
                .age(entity.getAge())
                .gender(entity.getGender())
                .disease1(entity.getDisease1())
                .disease2(entity.getDisease2())
                .disease3(entity.getDisease3())
                .surgery(entity.getSurgery())
                .activity1(entity.getActivity1())
                .activity2(entity.getActivity2())
                .activity3(entity.getActivity3())
                .users(entity.getUsers())
                .build();
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
