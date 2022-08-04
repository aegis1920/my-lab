package com.bingbong.timezonediff.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimezoneDiffRepository {
    List<PersonDto> selectAll();

    PersonDto select(@Param("id") String id);

    int create(PersonDto personDto);

    int createDbNow(String name);

    int update(@Param("id") String id, PersonDto personDto);

    void delete(@Param("id") String id);
}
