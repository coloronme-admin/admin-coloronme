package com.coloronme.admin.domain.personalColor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Entity
@Getter
@Setter
public class PersonalColor {
    @Id @GeneratedValue
    @Column(name = "PERSONAL_COLOR_ID")
    private Long id;
    private Long code;
}