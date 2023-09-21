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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "USER_ID")
    private Long userId;
    @NotNull
    private LocalDateTime consultDate;
    @NotNull
    private String consultContent;
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime deleteAt;

    public Consult(Long consultantId, Long userId, ConsultRequestDto consultRequestDto) {
        this.consultantId = consultantId;
        this.userId = userId;
        this.consultContent = consultRequestDto.getConsultContent();
        this.consultDate = consultRequestDto.getConsultDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
