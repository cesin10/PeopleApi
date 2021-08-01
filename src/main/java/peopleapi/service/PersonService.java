package peopleapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import peopleapi.dto.MessageResponseDTO;
import peopleapi.entity.Person;
import peopleapi.repository.PersonRepository;

@Service
public class PersonService {
	
private PersonRepository personRepository;
	

@Autowired	
public PersonService(PersonRepository personRepository) {
	 this.personRepository = personRepository;
}



	public MessageResponseDTO createPerson(Person person) {
		Person savedPerson = personRepository.save(person);
		return MessageResponseDTO
				.builder()
				.message("Created person with ID " + savedPerson.getId())
				.build();
		
	}

}
