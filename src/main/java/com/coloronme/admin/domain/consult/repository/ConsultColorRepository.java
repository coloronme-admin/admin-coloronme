package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.ConsultColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultColorRepository extends JpaRepository<ConsultColor, Integer> {
    List<ConsultColor> findByConsultId(int id);
}
