package com.bingbong.data;

import com.bingbong.domain.Movie;
import com.bingbong.domain.MovieRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Objects;

@Repository
public class JpaMovieRepository implements MovieRepository {
	
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public Movie save(Movie entity) {
		if (Objects.nonNull(entity.getId())) {
			// ID가 있으면 이미 있다는 소리니까 갱신
			return entityManager.merge(entity);
		}
		
		// 저장
		entityManager.persist(entity);
		return entity;
	}
	
	@Override
	public Movie findByDirector(String director) {
		String jpql = "select m from Movie m where m.director = :director";
		var query = entityManager.createQuery(jpql, Movie.class);
		query.setParameter("director", director);
		
		return query.getSingleResult();
	}
	
	// 내부적으로 EntityManager가 ThreadSafe하게 만들어놓는다.
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
