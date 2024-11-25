package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.ColorPersonalColorType;
import com.coloronme.product.personalColor.entity.PersonalColorType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorPersonalColorTypeRepository extends JpaRepository<ColorPersonalColorType, Integer> {
    @Modifying
    @Query(value = """
            DELETE
            FROM ColorPersonalColorType c
            WHERE c.personalColorType = :personalColorType
            """)
    void deleteByPersonalColorType(@Param("personalColorType") PersonalColorType personalColorType);
}
