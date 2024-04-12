package com.runit.user.repository;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.util.ContractType;

import java.util.List;
import java.util.Optional;

public interface PersonFileRepository {

    List<PersonDto> readAllPersons();
    List<PersonDto> findByContractType(ContractType contractType);
    void savePerson(ContractType type,PersonDto personDto);
    boolean deletePerson(String id);
    Person updatePerson(String id, Person existing, Person person);
    Person findById(String id);
    Optional<String> getFilePathForPersonId(List<String> files, String personId);
}
