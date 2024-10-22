package com.coloronme.product.personalColor.repository;

import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.response.PersonalColorDto;
import com.coloronme.product.personalColor.entity.PersonalColor;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalColorRepository extends JpaRepository<PersonalColor, Integer> {
    @Query(value = """
            SELECT new com.coloronme.product.personalColor.dto.response.PersonalColorDto(p.name, c.id, c.name, c.r, c.g, c.b)
            FROM Color c
            JOIN PersonalColor p ON c.personalColorId = p.id
            WHERE p.colorGroup = :colorGroup
            ORDER BY c.personalColorId
            """) /* 커스텀 dto를 사용하기위해 Jpql 사용*/
    List<PersonalColorDto> findPersonalColorByGroup(@Param("colorGroup") ColorGroup colorGroup);
}
