package com.coloronme.admin.domain.repository;

import com.coloronme.admin.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);
    boolean existsByEmail(String email);
    Member findByEmail(String email);
}
