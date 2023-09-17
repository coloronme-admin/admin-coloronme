package com.coloronme.admin.domain.consultant.entity;


import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;


    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Consultant(ConsultantRequestDto consultantRequestDto, RoleType roleType) {
        this.email = consultantRequestDto.getEmail();
        this.roleType =roleType;
        this.password = consultantRequestDto.getPassword();
    }
}
