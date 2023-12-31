package com.personal.doctor.CapstoneDesign.userDetail.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.personal.doctor.CapstoneDesign.user.domain.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "details_id")
    private Long id;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String gender;

    @Column // 앓은 적이 있거나 앓고 있는 질병 최대 3개 선택하여 저장
    private String disease1;

    @Column
    private String disease2;

    @Column
    private String disease3;

    @Column // 수술 이력
    private String surgery;

    @Column // 최근에 하고 있는 격렬한 활동 최대 3개 선택하여 저장
    private String hobby1;

    @Column
    private String hobby2;

    @Column
    private String hobby3;

    @Column // 사용자 직업 저장
    private String job;

    @Column // 복용중인 약
    private String medicine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder
    public Details(String age, String gender, String disease1, String disease2, String disease3,
                   String surgery, String hobby1, String hobby2, String hobby3, String medicine, String job, Users users) {
        this.age = age;
        this.gender = gender;
        this.disease1 = disease1;
        this.disease2 = disease2;
        this.disease3 = disease3;
        this.surgery = surgery;
        this.hobby1 = hobby1;
        this.hobby2 = hobby2;
        this.hobby3 = hobby3;
        this.medicine = medicine;
        this.job = job;
        this.users = users;
    }

}
