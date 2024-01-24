package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.Consult;
import com.coloronme.admin.domain.mainpage.dto.response.ColorChartResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Integer> {
    Optional<Consult> findByMemberId(int memberId);
    Optional<Consult> findByMemberIdAndConsultantId(int memberId, int consultantId);
    List<Consult> findAllByConsultantId(int consultantId);

    @Query(value = """
            SELECT
            pc.code, COUNT(c), ROUND(COUNT(c) * 100.0 / (SELECT COUNT(subC) FROM "Consult" subC WHERE subC."consultantId" = :consultantId AND subC."consultedDate" BETWEEN :from AND :to), 1)
            FROM "Consult" c
            JOIN "PersonalColor" pc ON c."personalColorId" = pc.id
            WHERE c."consultantId" = :consultantId AND c."consultedDate" BETWEEN :from AND :to
            GROUP BY c."personalColorId", pc.code
            ORDER BY COUNT(c) DESC""", nativeQuery = true)
    List<Object[]> getUserDataByColor(@Param("consultantId") int consultantId,
                                                   @Param("from") LocalDate from,
                                                   @Param("to") LocalDate to);

}
