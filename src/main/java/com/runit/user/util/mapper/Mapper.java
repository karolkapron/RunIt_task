package com.runit.user.util.mapper;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.util.ContractType;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public PersonDto mapPersonToDto(Person person, ContractType type) {
        return new PersonDto(
                person.getFirstName(),
                person.getLastName(),
                person.getMobile(),
                person.getEmail(),
                person.getPesel(),
                type
        );
    }
    public Person mapDtoToPerson(PersonDto personDto, ContractType type) {
        return new Person(
                personDto.getFirstName(),
                personDto.getLastName(),
                personDto.getMobile(),
                personDto.getEmail(),
                personDto.getPesel(),
                type
        );
    }
}
