package com.coloronme.product.personalColor.entity;

import com.coloronme.product.personalColor.dto.ColorGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "`PersonalColor`")
public class PersonalColor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="`colorGroup`", nullable = false, columnDefinition = "`ColorGroup`")
    private ColorGroup colorGroup;

    @Column(name="`createdAt`")
    private LocalDateTime createdAt;

    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}