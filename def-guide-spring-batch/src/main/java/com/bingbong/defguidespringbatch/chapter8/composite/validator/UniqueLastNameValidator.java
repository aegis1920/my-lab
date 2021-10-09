package com.bingbong.defguidespringbatch.chapter8.composite.validator;

import com.bingbong.defguidespringbatch.chapter8.composite.domain.Customer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 직접 구현한 Validator 인터페이스 구현체
 * lastName 필드 값이 고유해야한다고 했을 때, 재시작 시에도 상태를 유지하기 위해
 * ItemStreamSupport를 상속해 ExecutionContext에 저장해야 한다.
 *
 */
public class UniqueLastNameValidator extends ItemStreamSupport implements Validator<Customer> {
	
	private Set<String> lastNames = new HashSet<>();
	
	@Override
	public void validate(Customer value) throws ValidationException {
		// 이미 Set에 있다면 예외 발생
		if (lastNames.contains(value.getLastName())) {
			throw new ValidationException("Duplicate last name was found: " + value.getLastName());
		}
		this.lastNames.add(value.getLastName());
	}
	
	@Override
	public void open(ExecutionContext executionContext) {
		String lastNames = getExecutionContextKey("lastNames");
		
		// 이전 ExecutionContext에 저장되어있는지 확인
		if (executionContext.containsKey(lastNames)) {
			// 만약 저장되어 있다면 해당 값으로 원복
			this.lastNames = (Set<String>) executionContext.get(lastNames);
		}
	}
	
	@Override
	public void update(ExecutionContext executionContext) {
		Iterator<String> itr = lastNames.iterator();
		Set<String> copiedLastNames = new HashSet<>();
		while (itr.hasNext()) {
			copiedLastNames.add(itr.next());
		}
		
		executionContext.put(getExecutionContextKey("lastNames"), copiedLastNames);
	}
}
