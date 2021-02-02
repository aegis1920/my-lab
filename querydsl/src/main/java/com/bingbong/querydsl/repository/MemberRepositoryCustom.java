package com.bingbong.querydsl.repository;

import com.bingbong.querydsl.dto.MemberSearchCondition;
import com.bingbong.querydsl.dto.MemberTeamDto;
import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
}
