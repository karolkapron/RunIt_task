package com.runit.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.runit.user.service.FillAndValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.repository.PersonFileRepository;
import com.runit.user.service.PersonService;
import com.runit.user.util.ContractType;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonFileRepository personFileRepository;
    private final FillAndValidate fillAndValidate;

    @Autowired
    public PersonServiceImpl(PersonFileRepository personFileRepository, FillAndValidate fillAndValidate) {
        this.personFileRepository = personFileRepository;
        this.fillAndValidate =fillAndValidate;
    }

    @Override
    public List<PersonDto> findAll() {
        return personFileRepository.readAllPersons();
    }

    @Override
    public List<PersonDto> findByLastName(String lastName){

        return personFileRepository.readAllPersons().stream()
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonDto> findByFirstName(String firstName){

        return personFileRepository.readAllPersons().stream()
                    .filter(person -> person.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonDto> findByMobile(String mobile){

        return personFileRepository.readAllPersons().stream()
                .filter(person -> person.getMobile().equalsIgnoreCase(mobile))
                .collect(Collectors.toList());
    }

    @Override
    public String create(ContractType type, PersonDto person){
        String validationResponse = fillAndValidate.validatePersonDto(person);
        if(validationResponse != null){
            return validationResponse;
        }
        personFileRepository.savePerson(type, person);
        return "OK";
    }

    @Override
    public boolean deleteById(String id){
        return personFileRepository.deletePerson(id);
    }

    @Override
    public void modify(String id, Person person){
        if (person == null) {
            throw new IllegalArgumentException("Person or Person ID must not be null");
        }
        Person existingPerson = personFileRepository.findById(id);
        Person changedPerson = personFileRepository.updatePerson(id, existingPerson, person);

    }

    @Override
    public List<PersonDto> findByContractType(ContractType contractType){
        return personFileRepository.findByContractType(contractType);
    }
}
