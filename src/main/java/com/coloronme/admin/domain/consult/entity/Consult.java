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
public class Consult {
    @Id @GeneratedValue
    @Column(name="CONSULT_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "CONSULTANT_ID")
    private Consultant consultant;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "PERSONAL_COLOR_ID")
    private PersonalColor personalColor;
    @NotNull
    private LocalDateTime consultDate;
    private String consultContent;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;
    private LocalDateTime deleteAt;

    public Consult(Consultant consultant, Member member, PersonalColor personalColor, ConsultRequestDto consultRequestDto) {
        this.consultant = consultant;
        this.member = member;
        this.personalColor = personalColor;
        this.consultContent = consultRequestDto.getConsultContent();
        this.consultDate = consultRequestDto.getConsultDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
