package application.service;

import application.aop.CustomLogging;
import application.domain.Person;
import application.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InnerService {
	
	private final PersonRepository personRepository;
	
	@Transactional
	@CustomLogging
	public void saveBCWithNoException() {
		personRepository.save(new Person("B"));
		personRepository.save(new Person("C"));
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@CustomLogging
	public void saveBCWithNoExceptionWithRequiresNew() {
		personRepository.save(new Person("B"));
		personRepository.save(new Person("C"));
	}
	
	@Transactional
	@CustomLogging
	public void saveBCWithCException() {
		personRepository.save(new Person("B"));
		throw new RuntimeException("Runtime 예외");
//		personRepository.save(new Person("C"));
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@CustomLogging
	public void saveBCWithCExceptionWithRequiresNew() {
		personRepository.save(new Person("B"));
		throw new RuntimeException("Runtime 예외");
//		personRepository.save(new Person("C"));
	}
	
	@Transactional
	@CustomLogging
	public void savePersonWithExceptionAndCatch() {
			personRepository.save(new Person("B"));
		try {
			personRepository.save(new Person("C"));
			throw new RuntimeException("Runtime 예외");
		} catch (Exception e) {
			log.error("Runtime 예외 로깅", e);
		}
	}
}
