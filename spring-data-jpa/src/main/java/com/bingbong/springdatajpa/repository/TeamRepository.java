package com.bingbong.springdatajpa.repository;

import com.bingbong.springdatajpa.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
