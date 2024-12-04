package com.coloronme.product.personalColor.entity;

import com.coloronme.admin.domain.consult.entity.ColorPersonalColorType;
import com.coloronme.admin.domain.consult.entity.Consult;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="`PersonalColorType`")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalColorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="`consultantId`")
    private Integer consultantId;

    @Setter
    @Column(name="`personalColorTypeName`")
    private String personalColorTypeName;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="`personalColorGroupId`")
    private PersonalColorGroup personalColorGroup;

    @Setter
    @OneToMany(mappedBy = "personalColorType", cascade = CascadeType.ALL)
    private List<ColorPersonalColorType> colorPersonalColorTypeList;

    @OneToMany(mappedBy = "personalColorType", cascade = CascadeType.ALL)
    private List<Consult> consults;

    @Setter
    @Column(name = "`isDeleted`", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name="`createdAt`")
    private LocalDateTime createdAt;

    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
