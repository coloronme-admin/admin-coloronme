package com.coloronme.admin.domain.personalColor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "PersonalColor")
public class PersonalColor {
    @Id @GeneratedValue
    private Long id;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}