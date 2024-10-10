package com.coloronme.product.personalColor.repository;

import com.coloronme.product.personalColor.entity.PersonalColorGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonalColorGroupRepository extends JpaRepository<PersonalColorGroup, Integer> {
    /*@Query(value = """
            SELECT g.id AS personalColorGroupId, g."personalColorGroupName" AS personalColorGroupName,
                   t.id AS personalColorTypeId, t."personalColorTypeName" AS personalColorTypeName
            FROM "PersonalColorGroup" g
            JOIN "PersonalColorType" t ON g.id = t."personalColorGroupId"
            WHERE t."consultantId" = :consultantId
            """, nativeQuery = true)
    List<Object[]> findAllWithPersonalColorTypes(Integer consultantId);
*/

    @Query(value = """
        SELECT g FROM PersonalColorGroup g JOIN FETCH g.personalColorTypes t WHERE t.consultantId = :consultantId
        """)
    List<PersonalColorGroup> findAllByConsultantIdWithTypes(@Param("consultantId") Integer consultantId);

}
