package com.coloronme.admin.domain.consultant.repository;

import com.coloronme.admin.domain.consultant.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByConsultantId(int id);
}