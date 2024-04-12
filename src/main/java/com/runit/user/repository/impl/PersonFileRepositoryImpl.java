package com.runit.user.repository.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.model.PersonFile;
import com.runit.user.repository.PersonFileRepository;
import com.runit.user.util.ContractType;
import com.runit.user.util.XmlReader;
import com.runit.user.util.XmlSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class PersonFileRepositoryImpl implements PersonFileRepository {

    private static final String INTERNAL_PATH = "src/main/resources/static/Internal";
    private static final String EXTERNAL_PATH = "src/main/resources/static/External";

    private final XmlReader xmlReader;
    private final XmlSaver xmlSaver;

    @Autowired
    public PersonFileRepositoryImpl(XmlReader xmlReader, XmlSaver xmlSaver){

        this.xmlReader = xmlReader;
        this.xmlSaver = xmlSaver;
    }

    @Override
    public List<PersonDto> readAllPersons() {
        try {
            List<String> files = Arrays.asList(
                    INTERNAL_PATH,
                    EXTERNAL_PATH
            );
            return xmlReader.readPersonsFromXmlDirectories(files);
        } catch (Exception e) {
            throw new RuntimeException("Could not read XML file", e);
        }
    }

    @Override
    public List<PersonDto> findByContractType(ContractType contractType){
        try{
            return contractType.equals(ContractType.EXTERNAL) ?
                    xmlReader.readPersonsFromDirectory(EXTERNAL_PATH)
                    : xmlReader.readPersonsFromDirectory(INTERNAL_PATH);
        } catch (Exception e){
            throw new RuntimeException(("Could not read XML file" + e ));
        }
    }

    @Override
    public void savePerson(ContractType type, PersonDto personDto){
            try {
                xmlSaver.savePersonToXml(personDto, type);
            } catch (Exception e){
                throw new RuntimeException("Something gone wrong" + e);
            }
    }


    @Override
    public boolean deletePerson(String id){
        List<String> files = Arrays.asList(
                INTERNAL_PATH,
                EXTERNAL_PATH
        );
        try {
            List<PersonFile> personFiles = xmlReader.createPersonsFiles(files);
            if(personFiles != null) {
                Optional<PersonFile> response = personFiles.stream()
                        .filter(personFile -> personFile.getPerson().getPersonId().equals(id))
                        .findFirst();
                if (response.isPresent()) {
                    String path = response.map(PersonFile::getFileName).orElse(null);
                        return xmlReader.deleteXmlFile(path);
                }
            }
            return false;

        }catch (Exception e){
            throw new RuntimeException("Something gone wrong" + e);
        }
    }

    @Override
    public Person findById(String id) {
        List<String> files = Arrays.asList(
                INTERNAL_PATH,
                EXTERNAL_PATH
        );
        List<PersonFile> personFiles = null;
        try {
            personFiles = xmlReader.createPersonsFiles(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Optional<PersonFile> response = personFiles != null ? personFiles.stream()
                .filter(personFile -> personFile.getPerson().getPersonId().equals(id))
                .findFirst() : Optional.empty();

        return response.get().getPerson();
    }

    @Override
    public Person updatePerson(String id, Person existingPerson, Person newPersonData) {

        List<String> files = Arrays.asList(
                INTERNAL_PATH,
                EXTERNAL_PATH
        );
        String path = null;
        try {
            Optional<String> filePathForPersonId = getFilePathForPersonId(files, id);
            path = filePathForPersonId.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (newPersonData.getFirstName() != null) {
            existingPerson.setFirstName(newPersonData.getFirstName());
        }
        if (newPersonData.getLastName() != null) {
            existingPerson.setLastName(newPersonData.getLastName());
        }
        if (newPersonData.getMobile() != null) {
            existingPerson.setMobile(newPersonData.getMobile());
        }
        if (newPersonData.getEmail() != null) {
            existingPerson.setEmail(newPersonData.getEmail());
        }
        if (newPersonData.getPesel() != null) {
            existingPerson.setPesel(newPersonData.getPesel());
        }
        try {
            xmlSaver.updatePersonXml(existingPerson, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existingPerson;
    }

    @Override
    public Optional<String> getFilePathForPersonId(List<String> files, String personId) {
        try {
            List<PersonFile> personFiles = xmlReader.createPersonsFiles(files);
            return personFiles.stream()
                    .filter(pf -> pf.getPerson().getPersonId().equals(personId))
                    .map(PersonFile::getFileName)
                    .findFirst();
        } catch (Exception e) {
            System.out.println("Error reading XML files: " + e.getMessage());
            return Optional.empty();
        }
    }
}

