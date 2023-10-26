package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Integer> {
    Optional<Consult> findByMemberId(int memberId);
    Optional<Consult> findByMemberIdAndConsultantId(int memberId, int consultantId);
    List<Consult> findAllByConsultantId(int consultantId);
}