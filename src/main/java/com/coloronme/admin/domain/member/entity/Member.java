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
@Table(name = "`User`")
public class Member {
    @Id @GeneratedValue
    private int id;
    private String email;
    private String password;
    @Column(name="`profileImageUrl`")
    private String profileImageUrl;
    private String nickname;
    @Column(name="`personalColorId`")
    private int personalColorId;
    @Column(name="`worstColorId`")
    private int worstColorId;
    @Column(name="`userAuthDetailId")
    private int userAuthDetailId;
    @Enumerated(EnumType.STRING)
    private Gender gender; /*male, female, etc*/
    private String age;
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
