package com.coloronme.admin.domain.consult.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonalColor {
    @Id @GeneratedValue
    private Long id;
    private Long code;
}