package com.coloronme.admin.domain.consult.entity;

import com.coloronme.product.color.entity.Color;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`ConsultColor`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="`colorId`")
    private Color color;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="`consultId`")
    private Consult consult;

    public ConsultColor(Color color, Consult consult) {
        this.color = color;
        this.consult = consult;
    }
}
