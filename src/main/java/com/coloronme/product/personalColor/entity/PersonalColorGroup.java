package com.coloronme.product.personalColor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="`PersonalColorGroup`")
@Setter
@Getter
public class PersonalColorGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="`personalColorGroupName`")
    private String personalColorGroupName;
    @OneToMany(mappedBy = "personalColorGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalColorType> personalColorTypes;
}
