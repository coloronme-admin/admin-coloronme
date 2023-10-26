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
@Table(name = "Consult")
public class Consult {
    @Id @GeneratedValue
    private Long id;
    private Long consultantId;
    private Long memberId;
    private Long personalColorId;
    @NotNull
    private LocalDateTime consultDate;
    private String consultContent;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;
    private LocalDateTime deleteAt;

    public Consult(Long consultant, Long member, Long personalColor, ConsultRequestDto consultRequestDto) {
        this.consultantId = consultant;
        this.memberId = member;
        this.personalColorId = personalColor;
        this.consultContent = consultRequestDto.getConsultContent();
        this.consultDate = consultRequestDto.getConsultDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
