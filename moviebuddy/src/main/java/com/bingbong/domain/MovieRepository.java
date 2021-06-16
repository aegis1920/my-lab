package com.bingbong.domain;

//@RepositoryDefinition(domainClass = Movie.class, idClass = Long.class) 얘를 줘도 등록이 된다.
public interface MovieRepository {
	
	Movie save(Movie entity);
	
	Movie findByDirector(String director);
}
