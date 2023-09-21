package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.PersonalColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalColorRepository extends JpaRepository<PersonalColor, Long> {
}
