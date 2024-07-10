package com.coloronme.admin.domain.consult.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`ConsultColor`")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="`colorId`")
    private Integer colorId;

    @Column(name="`consultId`")
    private Integer consultId;
}
