package com.coloronme.product.personalColor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="`PersonalColorType`")
@Setter
@Getter
public class PersonalColorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="`consultantId`")
    private int consultantId;

    @Column(name="`personalColorTypeName`")
    private String personalColorTypeName;

    @Column(name="`personalColorTypeGroup`")
    private String personalColorTypeGroup;

    @Column(name="`createdAt`")
    private LocalDateTime createdAt;

    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
