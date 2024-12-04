package com.coloronme.admin.domain.consult.entity;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.product.personalColor.entity.PersonalColorType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`Consult`")
public class Consult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="`consultantId`")
    private Integer consultantId;

    @Column(name="`memberId`")
    private Integer memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="`personalColorTypeId`")
    private PersonalColorType personalColorType;

    @NotNull
    @Column(name="`consultedDate`")
    private LocalDateTime consultedDate;

    @Column(name="`consultedContent`")
    private String consultedContent;

    @Column(name="`consultedDrawing`")
    private String consultedDrawing;

    private String uuid;

    @OneToMany(mappedBy = "consult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsultColor> consultColors;

    @Column(name="`consultedFile`")
    private String consultedFile;

    @NotNull
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;

    public Consult(int consultantId, int memberId, PersonalColorType personalColorType, ConsultRequestDto consultRequestDto) {
        this.consultantId = consultantId;
        this.memberId = memberId;
        this.personalColorType = personalColorType;
        this.consultedContent = consultRequestDto.getConsultedContent();
        this.consultedDate = consultRequestDto.getConsultedDate();
        this.uuid = consultRequestDto.getUuid();
        this.consultedFile = consultRequestDto.getConsultedFile();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
