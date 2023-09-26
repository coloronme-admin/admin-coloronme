package com.coloronme.admin.domain.consultant.entity;

import com.coloronme.admin.domain.consultant.dto.request.ConsultantRequestDto;
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
public class Consultant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String company;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
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
}