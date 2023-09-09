package com.coloronme.admin.Domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

    @Column(name = "memberEmail", nullable = false)
    private String memberEmail;

    public RefreshToken(String token, String email) {
        this.refreshToken = token;
        this.memberEmail = email;
    }

    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}
