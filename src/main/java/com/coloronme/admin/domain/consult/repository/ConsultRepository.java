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
            pc.code, COUNT(c), ROUND(COUNT(c) * 100.0 / (SELECT COUNT(subC) FROM "Consult" subC WHERE subC."consultantId" = :consultantId AND subC."consultedDate" BETWEEN date(:from) AND date(:to)+1), 1)
            FROM "Consult" c
            JOIN "PersonalColor" pc ON c."personalColorId" = pc.id
            WHERE c."consultantId" = :consultantId AND c."consultedDate" BETWEEN date(:from) AND date(:to)+1
            GROUP BY c."personalColorId", pc.code
            ORDER BY COUNT(c) DESC""", nativeQuery = true)
    List<Object[]> getUserDataByColor(@Param("consultantId") int consultantId,
                                      @Param("from") String from, @Param("to") String to);

    @Query(value = """
            SELECT u.gender FROM "Consult" c 
            JOIN "User" u ON c."memberId" = u.id 
            WHERE c."consultantId" = :consultantId AND c."consultedDate" BETWEEN date(:from) AND date(:to)+1     
            """, nativeQuery = true)
    List<Object[]> getUserDataByGender(@Param("consultantId") int consultantId,
                                       @Param("from") String from, @Param("to") String to);

    @Query(value = """
            SELECT COUNT(c)
            FROM "Consult" c
            WHERE c."consultantId" = :consultantId AND c."consultedDate" BETWEEN date(:from) AND date(:to)+1
            """, nativeQuery = true)
    int getUserDataCountByDate(@Param("consultantId")int consultantId,
                               @Param("from") String from, @Param("to") String to);

    @Query(value = """
            SELECT *
            FROM "Consult" c
            WHERE c."consultantId" = :consultantId AND c."consultedDate" BETWEEN date(:from) AND date(:to)+1
            """, nativeQuery = true)
    List<Consult> getUserDataByDate(@Param("consultantId")int consultantId,
                                    @Param("from") String from, @Param("to") String to);

    @Query(value = """
            SELECT * FROM "Consult"
            WHERE EXTRACT(DOW FROM "consultedDate") = :dateNumber
            AND "consultantId" = :consultantId
            AND "consultedDate" BETWEEN date(:from) AND date(:to)+1
            """, nativeQuery = true)
    List<Consult> getUserDataByDateNumber(@Param("consultantId") int consultantId, @Param("dateNumber") int dateNumber,
                                          @Param("from") String from, @Param("to") String to);



}
