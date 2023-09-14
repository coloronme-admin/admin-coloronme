package com.coloronme.admin.domain.consultant.entity;


import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Authority authority;

    public Consultant(ConsultantRequestDto consultantRequestDto, Authority authority) {
        this.email = consultantRequestDto.getEmail();
        this.authority =authority;
        this.password = consultantRequestDto.getPassword();
    }
}
