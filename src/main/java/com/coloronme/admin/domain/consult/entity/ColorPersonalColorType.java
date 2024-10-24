package com.coloronme.admin.domain.consult.entity;

import com.coloronme.product.color.entity.Color;
import com.coloronme.product.personalColor.entity.PersonalColorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "`ColorPersonalColorType`")
@Entity
public class ColorPersonalColorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`colorId`")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`personalColorTypeId`")
    private PersonalColorType personalColorType;
}
