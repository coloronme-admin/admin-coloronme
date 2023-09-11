package com.coloronme.admin.repository;

import com.coloronme.admin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);
    boolean existsByEmail(String email);
    Member findByEmail(String email);
}
