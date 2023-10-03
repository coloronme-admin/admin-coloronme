package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
    Optional<Consult> findByMemberId(Long memberId);
    Optional<Consult> findByMemberIdAndConsultantId(Long memberId, Long consultantId);
    List<Consult> findAllByConsultantId(Long consultantId);
}
