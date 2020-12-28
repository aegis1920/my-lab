package application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionPropagationTest {

    @Autowired
    private PersonService personService;

    @Test
    void name() {
        personService.a();
    }
}
