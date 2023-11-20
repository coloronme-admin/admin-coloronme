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
// @DynamicUpdate /*User는 읽는 용 테이블이기 때문에 수정할 데이터만 수정되도록 설정*/
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
    @Column(name="gender", columnDefinition = "character_varying")
    private GenderEnum gender; /*male, female, etc*/
    private Integer age;
    @Column(name="`createdAt`")
    private LocalDateTime createdAt;
    @Column(name="`updatedAt`")
    private LocalDateTime updatedAt;
    @Column(name="`deletedAt`")
    private LocalDateTime deletedAt;
}
