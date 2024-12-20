package com.coloronme.admin.domain.consultant.entity;

import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import com.coloronme.admin.domain.mypage.dto.request.MyPageRequestDto;

import com.coloronme.admin.domain.mypage.dto.request.PasswordRequestDto;
import com.coloronme.product.personalColor.dto.ColorGroup;
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
    private Integer id;

    private String name;

    private String company;

    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="`roleType`", nullable = false, columnDefinition = "`RoleType`")
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @Column(name="`colorGroup`", nullable = false, columnDefinition = "`ColorGroup`")
    private ColorGroup colorGroup;

    @CreatedDate
    @Column(name="`createdAt`", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="`updatedAt`", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`", nullable = false)
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
        this.colorGroup = consultantRequestDto.getColorGroup();
    }

    public void update(MyPageRequestDto myPageRequestDto) {
        this.name = myPageRequestDto.getName();
        this.company = myPageRequestDto.getCompany();
        this.email = myPageRequestDto.getEmail();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateName(MyPageRequestDto myPageRequestDto) {
        this.name = myPageRequestDto.getName();
        this.updatedAt = LocalDateTime.now();
    }
    public void updateCompany(MyPageRequestDto myPageRequestDto) {
        this.company = myPageRequestDto.getCompany();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEmail(MyPageRequestDto myPageRequestDto) {
        this.email = myPageRequestDto.getEmail();
        this.updatedAt = LocalDateTime.now();
    }
    public void updatePassword(PasswordRequestDto passwordRequestDto) {
        this.password = passwordRequestDto.getNewPassword();
    }
}