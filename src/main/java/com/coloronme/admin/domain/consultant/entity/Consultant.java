package com.coloronme.admin.domain.consultant.entity;

import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import com.coloronme.admin.domain.mypage.dto.MyPageRequestDto;

import com.coloronme.admin.domain.mypage.dto.PasswordRequestDto;
import com.coloronme.admin.domain.mypage.dto.PasswordResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`Consultant`")
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String company;
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name="`roleType`")
    private RoleType roleType;
    @CreatedDate
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;

    @PrePersist //DB에 INSERT 되기 전에 실행, DB에 값을 넣으면 자동으로 실행됨
    public void CreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public Consultant(ConsultantRequestDto consultantRequestDto, RoleType roleType) {
        this.email = consultantRequestDto.getEmail();
        this.roleType = roleType;
        this.name = getName();
        this.company = getCompany();
        this.password = consultantRequestDto.getPassword();
    }

    public void update(MyPageRequestDto myPageRequestDto) {
        this.name = myPageRequestDto.getName();
        this.company = myPageRequestDto.getCompany();
        this.email = myPageRequestDto.getEmail();
        this.updatedAt = LocalDateTime.now();
    }
    public void updatePassword(PasswordRequestDto passwordRequestDto) {
        this.password = passwordRequestDto.getNewPassword();
    }
}