package com.coloronme.admin.domain.consultant.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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