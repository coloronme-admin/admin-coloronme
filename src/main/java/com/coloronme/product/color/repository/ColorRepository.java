package com.coloronme.product.color.repository;

import com.coloronme.product.color.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}
