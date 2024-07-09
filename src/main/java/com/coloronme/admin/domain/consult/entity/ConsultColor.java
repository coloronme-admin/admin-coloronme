package com.coloronme.admin.domain.consult.entity;

import com.coloronme.product.color.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`ConsultColor`")
@Getter
@Setter
public class ConsultColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="`colorId`")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="`consultId`")
    private Consult consult;
}
