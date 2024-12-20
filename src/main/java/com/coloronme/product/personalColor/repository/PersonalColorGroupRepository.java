package com.coloronme.product.personalColor.repository;

import com.coloronme.product.personalColor.entity.PersonalColorGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonalColorGroupRepository extends JpaRepository<PersonalColorGroup, Integer> {
    @Query(value = """
        SELECT g FROM PersonalColorGroup g JOIN FETCH g.personalColorTypes t WHERE t.consultantId = :consultantId
        """)
    List<PersonalColorGroup> findAllByConsultantIdWithTypes(@Param("consultantId") Integer consultantId);

    PersonalColorGroup findByPersonalColorGroupName(String personalColorGroupName);

    @Query(value = """
           SELECT
                pcg
           FROM PersonalColorGroup pcg
           JOIN FETCH pcg.personalColorTypes t
           WHERE t.id = :personalColorTypeId
           """)
    Optional<PersonalColorGroup> findByPersonalColorTypeId(int personalColorTypeId);
}
