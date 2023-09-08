package com.coloronme.admin.repository;

import com.coloronme.admin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member saveMember(Member member);
    public boolean existsByEmail(String email);
    public Member findByEmail(String email);

    @Query("UPDATE MEMBER m SET m.REFRESH_TOKEN = :refreshToken WHERE m.email = :email")
    void updateMemberRefreshToken(String email, String refreshToken);
}
