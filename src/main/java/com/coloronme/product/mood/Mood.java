package com.coloronme.product.mood;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "`Mood`")
public class Mood {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String code;
    @Column(name="`personalColorId`")
    private int personalColorId;
    @Column(name="`worstColorId`")
    private int worstColorId;
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
