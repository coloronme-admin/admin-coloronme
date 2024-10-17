package com.coloronme.product.personalColor.repository;

import com.coloronme.product.personalColor.entity.PersonalColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalColorRepository extends JpaRepository<PersonalColor, Integer> {
    List<PersonalColor> findByColorGroup(String pccs);
}
