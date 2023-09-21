package com.coloronme.admin.domain.consult.repository;

import com.coloronme.admin.domain.consult.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

}
