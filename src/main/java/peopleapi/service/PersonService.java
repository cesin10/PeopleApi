package peopleapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import peopleapi.dto.MessageResponseDTO;
import peopleapi.dto.request.PersonDTO;
import peopleapi.entity.Person;
import peopleapi.mapper.PersonMapper;
import peopleapi.repository.PersonRepository;

@Service
public class PersonService {
	
private PersonRepository personRepository;

private final PersonMapper personMapper = PersonMapper.INSTANCE;
	

@Autowired	
public PersonService(PersonRepository personRepository) {
	 this.personRepository = personRepository;
}



	public MessageResponseDTO createPerson(PersonDTO personDTO) {

		//É necessário converter um tipo DTO para Entity porque o JPA não lida com DTO
		Person personToSave = personMapper.toModel(personDTO);
		
		Person savedPerson = personRepository.save(personToSave);
		return MessageResponseDTO
				.builder()
				.message("Created person with ID " + savedPerson.getId())
				.build();
		
	}

}
