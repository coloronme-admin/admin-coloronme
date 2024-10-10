package com.coloronme.product.member.entity;

import com.coloronme.product.member.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "`User`")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    @Column(name="`profileImageUrl`")
    private String profileImageUrl;
    private String nickname;
    @Column(name="`personalColorId`")
    private Integer personalColorId;
    @Column(name="`worstColorId`")
    private Integer worstColorId;
    @Column(name="`userAuthDetailId`")
    private Integer userAuthDetailId;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private int age;
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
