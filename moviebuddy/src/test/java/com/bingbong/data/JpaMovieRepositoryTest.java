package com.bingbong.data;

import com.bingbong.MovieBuddyApplication;
import com.bingbong.domain.Movie;
import com.bingbong.domain.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(MovieBuddyApplication.class)
@Transactional
class JpaMovieRepositoryTest {
	
	@Autowired
	MovieRepository movieRepository;
	
	@Test
	void save() {
		var movie = new Movie("고질라 VS 콩", "아무개");
		Movie save = movieRepository.save(movie);
		
		assertNotNull(save.getId());
	}
	
	@Test
	void savesAndFindMovieByDirector() {
		var movie = new Movie("고질라 VS 콩", "아무개");
		Movie save = movieRepository.save(movie);
		assertNotNull(save.getId());

		var result = movieRepository.findByDirector(movie.getDirector());
		assertEquals(save, result);
		assertEquals(save.getTitle(), result.getTitle());
	}
}