package peopleapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import peopleapi.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
