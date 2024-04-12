package com.runit.user.service;

import java.util.List;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.util.ContractType;

public interface PersonService {

    List<PersonDto> findAll();
    List<PersonDto> findByLastName(String lastName);
    List<PersonDto> findByFirstName(String firstName);
    List<PersonDto> findByMobile(String mobile);
    String create(ContractType type, PersonDto person);
    boolean deleteById(String id);
    void modify(String id, Person person);
    List<PersonDto> findByContractType(ContractType contractType);
}
