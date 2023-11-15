package com.coloronme.product.member.entity;

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
@Table(name = "`UserAuthDetail`")
public class UserAuthDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uuid;
    @Column(name="`uuidExpiredAt`")
    private String uuidExpiredAt;
    @Column(name="`currentHashedRefreshToken`")
    private String currentHashedRefreshToken;
    @Column(name="`agreedToTerms`")
    private boolean agreedToTerms;
    @Column(name="`agreedToPrivacy`")
    private boolean agreedToPrivacy;
    @Column(name="`isAgeRequirementAgreed`")
    private boolean isAgeRequirementAgreed;
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
