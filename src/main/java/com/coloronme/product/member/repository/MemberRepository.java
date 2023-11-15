package com.coloronme.product.member.repository;

import com.coloronme.product.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUserAuthDetailId(int id);
}
