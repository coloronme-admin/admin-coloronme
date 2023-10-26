package com.coloronme.admin.domain.consult.entity;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import com.coloronme.admin.domain.consultant.entity.Consultant;
import com.coloronme.admin.domain.member.entity.Member;
import com.coloronme.admin.domain.personalColor.entity.PersonalColor;
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
    @Id @GeneratedValue
    private int id;
    @Column(name="`consultantId`")
    private int consultantId;
    @Column(name="`memberId`")
    private int memberId;
    @Column(name="`personalColorId`")
    private int personalColorId;
    @NotNull
    @Column(name="`consultDate`")
    private LocalDateTime consultDate;
    @Column(name="`consultContent`")
    private String consultContent;
    @NotNull
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @NotNull
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deleteAt`")
    private LocalDateTime deleteAt;

    public Consult(int consultantId, int memberId, int personalColorId, ConsultRequestDto consultRequestDto) {
        this.consultantId = consultantId;
        this.memberId = memberId;
        this.personalColorId = personalColorId;
        this.consultContent = consultRequestDto.getConsultContent();
        this.consultDate = consultRequestDto.getConsultDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
