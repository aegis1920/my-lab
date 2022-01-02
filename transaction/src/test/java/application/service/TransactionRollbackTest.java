package application.service;

import application.domain.Person;
import application.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TransactionRollbackTest {
	
	@Autowired
	private PersonService personService;
	
	@Test
	void name1() {
		Person createdPerson = personService.create(new Person("bingbong", 20));
		
		assertThat(personService.read(createdPerson.getId()).getName()).isEqualTo("bingbong");
	}
	
	@Test
	void name() {
		Person createdPerson = personService.create(new Person("bingbong", 20));
		
		assertThatThrownBy(() -> personService.read(createdPerson.getId()))
				.isInstanceOf(IllegalArgumentException.class);
	}
}
