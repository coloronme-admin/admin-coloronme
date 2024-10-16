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
    private Integer id;

    @Column(name="`consultantId`")
    private Integer consultantId;

    @Column(name="`personalColorTypeName`")
    private String personalColorTypeName;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="`personalColorGroupId`")
    private PersonalColorGroup personalColorGroup;

    @Column(name="`createdAt`")
    private LocalDateTime createdAt;

    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
