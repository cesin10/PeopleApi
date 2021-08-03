package peopleapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import peopleapi.dto.MessageResponseDTO;
import peopleapi.dto.request.PersonDTO;
import peopleapi.entity.Person;
import peopleapi.exception.PersonNotFoundException;
import peopleapi.mapper.PersonMapper;
import peopleapi.repository.PersonRepository;

@Service
public class PersonService {
	
private PersonRepository personRepository;

//personMapper servirá para converter DtoToEntity e EntityToDto
private final PersonMapper personMapper = PersonMapper.INSTANCE;
	

@Autowired	
public PersonService(PersonRepository personRepository) {
	 this.personRepository = personRepository;
}	

    public List<PersonDTO> listAll(){
		
		List<Person> allPeople = personRepository.findAll(); 
		return allPeople.stream()
				        .map(personMapper::toDto)
				        .collect(Collectors.toList());	
	}
	
	
	
	public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
        return personMapper.toDto(person);
	}
	
	
	public void delete(Long id) throws PersonNotFoundException {
		verifyIfExists(id); 
		personRepository.deleteById(id);
		
	}
	
	
	public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {

		verifyIfExists(id);
		//É necessário converter um tipo DTO para Entity porque o JPA não lida com DTO
		Person personToUpdate = personMapper.toModel(personDTO);
		
		Person updatedPerson = personRepository.save(personToUpdate);
		return createMessageResponse(updatedPerson.getId(), "Updated Person with Id ");		
	}

	
	public MessageResponseDTO createPerson(PersonDTO personDTO) {

		//É necessário converter um tipo DTO para Entity porque o JPA não lida com DTO
		Person personToSave = personMapper.toModel(personDTO);
		
		Person savedPerson = personRepository.save(personToSave);
		return createMessageResponse(savedPerson.getId(),"Saved Person with Id ");
	}

	
	//Métodos 	
	
	
	private MessageResponseDTO createMessageResponse(Long id, String message) {
		return MessageResponseDTO
				.builder()
				.message(message + id)
				.build();
	}
	
	
	//Método auxiliar para verificar se o id existe na base de dados ou não
		private  Person verifyIfExists(Long id) throws PersonNotFoundException {
			return personRepository.findById(id)
	        .orElseThrow(()-> new PersonNotFoundException(id)); 
			
		}
	

}





















