package com.coloronme.admin.repository;

import com.coloronme.admin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    public Member saveMember(Member member);
    public int findByEmail(String email);
}
