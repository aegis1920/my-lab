package application.service;

import application.aop.CustomLogging;
import application.domain.Person;
import application.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OuterService {
	
	private final InnerService innerService;
	private final PersonRepository personRepository;
	
	@Transactional
	@CustomLogging
	public void saveABCDE() {
		personRepository.save(new Person("A"));
		innerService.saveBCWithNoException();
		personRepository.save(new Person("D"));
		personRepository.save(new Person("E"));
	}
	
	@Transactional
	@CustomLogging
	public void saveABCDEWithBException() {
		personRepository.save(new Person("A"));
		innerService.saveBCWithCException();
		personRepository.save(new Person("D"));
		personRepository.save(new Person("E"));
	}
	
	@Transactional
	@CustomLogging
	public void saveOuterPersonAndInnerPersonWithInnerExceptionAndCatch() {
		personRepository.save(new Person("A"));
//		innerService.savePerson();
		personRepository.save(new Person("D"));
		personRepository.save(new Person("E"));
	}
	
	@Transactional
	@CustomLogging
	public void saveABCDEWithEException() {
		personRepository.save(new Person("A"));
		innerService.saveBCWithNoException();
		personRepository.save(new Person("D"));
		throw new RuntimeException("Runtime 예외");
//		personRepository.save(new Person("E"));
	}
	
	@Transactional
	@CustomLogging
	public void saveABCDEWithEExceptionWithRequiresNew() {
		personRepository.save(new Person("A"));
		innerService.saveBCWithNoExceptionWithRequiresNew();
		personRepository.save(new Person("D"));
		throw new RuntimeException("Runtime 예외");
	}
	
	@Transactional
	@CustomLogging
	public void saveABCDEWithTryCatchAndCException() {
		personRepository.save(new Person("A"));
		try {
			innerService.saveBCWithCException();
		}catch (Exception e) {
			log.error("잡았습니다", e);
		}
		personRepository.save(new Person("D"));
		personRepository.save(new Person("E"));
	}
	
	@Transactional
	@CustomLogging
	public void saveABCDEWithTryCatchAndCExceptionWithRequiresNew() {
		personRepository.save(new Person("A"));
		try {
			innerService.saveBCWithCExceptionWithRequiresNew();
		}catch (Exception e) {
			log.error("잡았습니다", e);
		}
		personRepository.save(new Person("D"));
		personRepository.save(new Person("E"));
	}
}
