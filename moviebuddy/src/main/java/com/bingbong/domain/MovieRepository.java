package com.bingbong.domain;

public interface MovieRepository {
	
	Movie save(Movie entity);
	
	Movie findByDirector(String director);
}
