package com.bingbong.springdatajpa.repository;

import com.bingbong.springdatajpa.domain.Member;
import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
