package com.coloronme.admin.domain.member.entity;

import com.coloronme.admin.domain.member.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String uuid;
    private String email;
    private String password;
    private String profileImageUrl;
    private String nickname;
    private Long personalColorId;
    private Long worstColorId;
    private String currentHashedRefreshToken;
    private Gender gender; /*male, female, etc*/
    private String age;
    private boolean agreedToTerms;
    private boolean agreedToPrivacy;
    private boolean isAgeRequirementAgreed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
