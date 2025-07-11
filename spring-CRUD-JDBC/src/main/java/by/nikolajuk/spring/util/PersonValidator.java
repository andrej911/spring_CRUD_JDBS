package by.nikolajuk.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.nikolajuk.spring.dao.PersonDAO;
import by.nikolajuk.spring.models.Person;

@Component
public class PersonValidator implements Validator {

	private final PersonDAO personDAO;

	@Autowired
	public PersonValidator(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	public boolean supports(Class<?> aClass) {

		return Person.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Person person = (Person) o;

		if (personDAO.show(person.getEmail()).isPresent())
			errors.rejectValue("email", "", "This email is already taken");

		// посмотреьт, есть ли человек с таким email в БД
	}

}
