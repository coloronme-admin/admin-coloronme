package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
    List<Consult> findAllByConsultantId(Long consultantId);
}
