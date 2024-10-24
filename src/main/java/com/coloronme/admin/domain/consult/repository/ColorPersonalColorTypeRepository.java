package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.ColorPersonalColorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorPersonalColorTypeRepository extends JpaRepository<ColorPersonalColorType, Integer> {
}
