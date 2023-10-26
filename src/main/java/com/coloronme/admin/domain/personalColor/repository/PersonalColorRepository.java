package com.coloronme.admin.domain.personalColor.repository;

import com.coloronme.admin.domain.personalColor.entity.PersonalColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalColorRepository extends JpaRepository<PersonalColor, Integer> {
}
