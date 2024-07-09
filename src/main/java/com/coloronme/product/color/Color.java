package com.coloronme.product.color;

import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.consult.entity.ConsultColor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="`Color`")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String code;
    private String r;
    private String g;
    private String b;
    @Column(name = "`personalColorId`")
    private int personalColorId;

    @Column(name="`worstColorId`")
    private int worstColorId;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<ConsultColor> consultColors;

    @Column(name="`createdAt`")
    private LocalDateTime createdAt;

    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
