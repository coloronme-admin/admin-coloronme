package com.coloronme.admin.domain.consultant.entity;


import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    //enum 타입으로 속성 저장
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 탈퇴할 때 개인정보 유무 email이랑 비번 바꿔서 탈퇴한 사람이 로그인 안되게 막고,
    @CreatedDate
    private LocalDateTime createdAt;

    //개인 정보 수정
    private LocalDateTime updatedAt;

    //기업 계약 해지 유무
    private LocalDateTime deletedAt;

    @PrePersist //DB에 INSERT 되기 전에 실행, DB에 값을 넣으면 자동으로 실행됨
    public void CreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public Consultant(ConsultantRequestDto consultantRequestDto, RoleType roleType) {
        this.email = consultantRequestDto.getEmail();
        this.roleType = roleType;
        this.password = consultantRequestDto.getPassword();
    }
}