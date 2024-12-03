package com.coloronme.product.personalColor.repository;

import com.coloronme.product.personalColor.dto.ColorGroup;
import com.coloronme.product.personalColor.dto.response.PersonalColorDto;
import com.coloronme.product.personalColor.entity.PersonalColor;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalColorRepository extends JpaRepository<PersonalColor, Integer> {
    /*색상군별 조회*/
    @Query(value = """
            SELECT new com.coloronme.product.personalColor.dto.response.PersonalColorDto(p.name, c.id, c.name, c.r, c.g, c.b)
            FROM Color c
            JOIN PersonalColor p ON c.personalColorId = p.id
            WHERE p.colorGroup = :type
            ORDER BY c.id ASC, c.personalColorId ASC
            """) /* 커스텀 dto를 사용하기위해 Jpql 사용*/
    List<PersonalColorDto> findPersonalColorByType(@Param("type") ColorGroup type);

    /*색상군별 + 퍼스널 컬러별 조회*/
    @Query(value = """
            SELECT new com.coloronme.product.personalColor.dto.response.PersonalColorDto(p.name, c.id, c.name, c.r, c.g, c.b)
            FROM Color c
            JOIN PersonalColor p ON c.personalColorId = p.id
            WHERE p.colorGroup = :type and p.name = :group
            ORDER BY c.id ASC, c.personalColorId ASC
            """)
    List<PersonalColorDto> findPersonalColorByTypeAndGroup(@Param("type") ColorGroup type, @Param("group")String group);
}
