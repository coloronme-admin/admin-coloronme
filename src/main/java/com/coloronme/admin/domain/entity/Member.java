package com.coloronme.admin.domain.entity;

import com.coloronme.admin.global.enums.MemberRoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
    @Column(name = "ROLE_TYPE")
    private MemberRoleType MemberRoleType;
}
