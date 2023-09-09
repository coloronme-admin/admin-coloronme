package com.coloronme.admin.Domain.entity;


import com.coloronme.admin.Domain.dto.request.MemberRequestDto;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    private Authority authority;

    public Member(MemberRequestDto memberRequestDto, Authority authority) {
        this.email = memberRequestDto.getEmail();
        this.authority =authority;
        this.password = memberRequestDto.getPassword();
    }
}
