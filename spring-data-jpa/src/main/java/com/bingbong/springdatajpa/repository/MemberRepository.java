package com.bingbong.springdatajpa.repository;

import com.bingbong.springdatajpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
