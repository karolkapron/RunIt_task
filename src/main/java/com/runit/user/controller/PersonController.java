package com.runit.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.service.PersonService;
import com.runit.user.util.ContractType;

@RestController
@RequestMapping("api/persons")
public class PersonController {

    final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public List<PersonDto> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/by-last-name/{lastName}")
    public List<PersonDto> findByLastName(@PathVariable String lastName){
        return personService.findByLastName(lastName);
    }

    @GetMapping("/by-first-name/{firstName}")
    public List<PersonDto> findByFirstName(@PathVariable String firstName){
        return personService.findByFirstName(firstName);
    }

    @GetMapping("by-mobile/{mobile}")
    public List<PersonDto> findByMobile(@PathVariable String mobile){
        return personService.findByMobile(mobile);
    }

    @GetMapping("/by-contract-type/{contractType}")
    public List<PersonDto> findByContractType(@PathVariable("contractType") ContractType contractType){
        return personService.findByContractType(contractType);
    }

    @PostMapping("/{contractType}")
    public ResponseEntity<String> createPerson(@PathVariable("contractType") ContractType type , @RequestBody PersonDto person){

        String response = personService.create(type, person);

        if(response != null){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok("Person successfully saved to XML.");
    }

    @DeleteMapping("/{id}")
    public boolean deletePerson(@PathVariable String id){
        return personService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable String id,@RequestBody Person person){
        personService.modify(id, person);
    }
}
