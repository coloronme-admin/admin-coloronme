package com.coloronme.product.personalColor.entity;

import com.coloronme.product.color.Color;
import com.coloronme.product.member.entity.Member;
import com.coloronme.product.mood.Mood;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "`PersonalColor`")
public class PersonalColor {
    @Id @GeneratedValue
    private int id;
    private String code;
    private String name;
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}