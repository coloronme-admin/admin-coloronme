package com.coloronme.admin.domain.consult.entity;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name="`personalColorId`")
    private Integer personalColorId;

    @Column(name="`personalColorTypeId`")
    private Integer personalColorTypeId;

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
        this.personalColorTypeId = consultRequestDto.getPersonalColorTypeId();
        this.consultedContent = consultRequestDto.getConsultedContent();
        this.consultedDate = consultRequestDto.getConsultedDate();
        this.uuid = consultRequestDto.getUuid();
        this.consultedFile = consultRequestDto.getConsultedFile();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
