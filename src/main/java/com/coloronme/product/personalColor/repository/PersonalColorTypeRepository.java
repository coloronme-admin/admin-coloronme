package com.coloronme.product.personalColor.repository;

import com.coloronme.product.personalColor.dto.PersonalColorGroupEnum;
import com.coloronme.product.personalColor.entity.PersonalColorType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonalColorTypeRepository extends JpaRepository<PersonalColorType, Integer> {

    @Query(value = """
            SELECT
                pct
            FROM PersonalColorType pct
            JOIN FETCH pct.colorPersonalColorTypeList cpct
            JOIN FETCH cpct.color c
            WHERE pct.consultantId = :consultantId
            AND pct.personalColorGroup.personalColorGroupName = :personalColorGroup
            """)
    List<PersonalColorType> findPersonalColorTypeByGroup(@Param("consultantId") int consultantId,
                                                            @Param("personalColorGroup") String personalColorGroup);

    Optional<PersonalColorType> findByConsultantIdAndId(int consultantId, int personalColorTypeId);
}
