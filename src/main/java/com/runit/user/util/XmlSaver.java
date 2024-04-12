package com.runit.user.util;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.util.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

@Component
public class XmlSaver {

    private static final String INTERNAL_PATH = "src/main/resources/static/Internal";
    private static final String EXTERNAL_PATH = "src/main/resources/static/External";

    private final Mapper mapper;

    public XmlSaver(Mapper mapper) {
        this.mapper = mapper;
    }

    public void savePersonToXml(PersonDto personDto, ContractType contractType) throws JAXBException {
        validateInput(personDto, contractType);

        Person person = convertDtoToPerson(personDto, contractType);
        File file = prepareFile(person, contractType);

        serializePersonToXml(person, file);
    }

    private void validateInput(PersonDto personDto, ContractType contractType) {
        if (personDto == null || contractType == null) {
            throw new IllegalArgumentException("PersonDto and ContractType must not be null.");
        }
    }

    private Person convertDtoToPerson(PersonDto personDto, ContractType contractType) {
        Person person = mapper.mapDtoToPerson(personDto, contractType);
        if (person == null) {
            throw new IllegalStateException("Mapping of PersonDto to Person failed.");
        }
        return person;
    }

    private File prepareFile(Person person, ContractType contractType) {
        String directoryPath = determineDirectory(contractType);
        File directory = new File(directoryPath);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalStateException("Could not create directory at: " + directoryPath);
        }
        String fileName = person.getLastName() + "_" + person.getFirstName() + ".xml";
        return new File(directory, fileName);
    }

    private void serializePersonToXml(Person person, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Person.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(person, file);
    }

    private String determineDirectory(ContractType contractType) {
        return contractType == ContractType.INTERNAL ? INTERNAL_PATH : EXTERNAL_PATH;
    }

    public void updatePersonXml(Person person, String filePath) throws Exception {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalStateException("File does not exist at: " + filePath);
        }

        JAXBContext context = JAXBContext.newInstance(Person.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(person, file);
    }
}


