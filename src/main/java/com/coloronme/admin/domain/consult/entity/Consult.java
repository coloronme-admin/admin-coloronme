package com.coloronme.admin.domain.consult.entity;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.product.color.Color;
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
    private int id;

    @Column(name="`consultantId`")
    private int consultantId;

    @Column(name="`memberId`")
    private int memberId;

    @Column(name="`personalColorId`")
    private int personalColorId;

    @NotNull
    @Column(name="`consultedDate`")
    private LocalDateTime consultedDate;

    @Column(name="`consultedContent`")
    private String consultedContent;

    @Column(name="`consultedDrawing`")
    private String consultedDrawing;

    private String uuid;

    @Column(name="`consultedFile`")
    private String consultedFile;

    @OneToMany(mappedBy = "consult", cascade = CascadeType.ALL)
    private List<ConsultColor> consultColors;

    @NotNull
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;

    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;

    public Consult(int consultantId, int memberId, int personalColorId, ConsultRequestDto consultRequestDto) {
        this.consultantId = consultantId;
        this.memberId = memberId;
        this.personalColorId = personalColorId;
        this.consultedContent = consultRequestDto.getConsultedContent();
        this.consultedDate = consultRequestDto.getConsultedDate();
        this.uuid = consultRequestDto.getUuid();
        this.consultedFile = consultRequestDto.getConsultedFile();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
