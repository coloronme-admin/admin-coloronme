package com.coloronme.admin.domain.consult.entity;

import com.coloronme.admin.domain.consult.dto.request.ConsultRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Consult {
    @Id @GeneratedValue
    @Column(name="CONSULT_ID")
    private Long consultId;
    @Column(name = "CONSULTANT_ID")
    private Long consultantId;
    @Column(name = "MEMBER_ID")
    private Long memberId;
    @Column(name = "PERSONAL_COLOR_ID")
    private Long personalColorId;
    @NotNull
    private LocalDateTime consultDate;
    private String consultContent;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;
    private LocalDateTime deleteAt;

    public Consult(Long consultantId, Long userId, ConsultRequestDto consultRequestDto) {
        this.consultantId = consultantId;
        this.memberId = userId;
        this.personalColorId = consultRequestDto.getPersonalColorId();
        this.consultContent = consultRequestDto.getConsultContent();
        this.consultDate = consultRequestDto.getConsultDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
