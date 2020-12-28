package application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import application.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "classpath:application.yml")
public class TransactionRollbackTest {

    @Autowired
    private PersonService personService;

    @Test
    void name1() {
        Person createdPerson = personService.create(new Person("bingbong", 20L));

        assertThat(personService.read(createdPerson.getId()).getName()).isEqualTo("bingbong");
    }

    @Test
    void name() {
        Person createdPerson = personService.create(new Person("bingbong", 20L));

        assertThatThrownBy(() -> personService.read(createdPerson.getId()))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
