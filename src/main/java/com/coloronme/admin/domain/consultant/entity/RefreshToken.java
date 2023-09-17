package com.coloronme.admin.domain.consultant.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
// RefreshToken 을 따로 테이블을 두는 이유 : Consultant 안에 RefreshToken 을 두면 Consultant 테이블을 불러올 때 RefreshToken 을 같이 불러 오게 된다.
//
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

    @Column(name = "consultantEmail", nullable = false)
    private String consultantEmail;

    public RefreshToken(String token, String email) {
        this.refreshToken = token;
        this.consultantEmail = email;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}
