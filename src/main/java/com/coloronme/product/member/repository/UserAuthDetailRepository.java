package com.coloronme.product.member.repository;

import com.coloronme.product.member.entity.UserAuthDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthDetailRepository extends JpaRepository<UserAuthDetail, Integer> {
    Optional<UserAuthDetail> findByUuid(String uuid);
}
