package com.coloronme.admin.domain.consultant.repository;

import com.coloronme.admin.domain.consultant.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Integer> {
    Optional<Consultant> findByEmail(String email);
    Optional<Consultant> findById(int id);
    boolean existsByEmail(String email);
    Optional<Consultant> findByPassword(String password);
}
