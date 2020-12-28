package application.service;

import application.domain.Person;
import application.repository.PersonRepository;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person create(Person person) {
        Person result = null;
//        try {
            result = personRepository.save(person);
//            throw new IOException("");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return result;
    }

    @Transactional
    public Person read(Long id) {
        return personRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    public void a() {
        try {
            b();
        } catch (RuntimeException e) {
            String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
            System.out.println(transactionName);
        }
        b();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void b() {
        String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        System.out.println(transactionName);
    }
}
