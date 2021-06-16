package com.bingbong.domain;

import org.springframework.data.repository.Repository;

// extends Repository를 상속하면 Spring에서 발견하고 빈으로 만들어 등록하게 해준다.
// extends CrudRepository를 상속하면 CRUD 다 가능
// extends PagingAndSortingRepository를 상속하면 페이징, 소팅 관련 기능 가능
// SimpleJpaRepository라는 구현체가 있으며 프록시를 통해 객체를 만든다.
public interface MovieDataRepository extends Repository<Movie, Long> {
	
	Movie save(Movie entity);
	
	Movie findByDirector(String director);
}
