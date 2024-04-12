package com.runit.user.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.model.PersonFile;
import com.runit.user.util.mapper.Mapper;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Component
public class XmlReader {

    final Mapper mapper;

    public XmlReader(Mapper mapper) {
        this.mapper = mapper;
    }

    private ContractType determinePersonType(String directoryPath) {
        if (directoryPath.contains("Internal")) {
            return ContractType.INTERNAL;
        } else if (directoryPath.contains("External")) {
            return ContractType.EXTERNAL;
        }
        return ContractType.EXTERNAL;
    }


    public List<PersonDto> readPersonsFromXmlDirectories(List<String> directoryPaths) throws Exception {
        List<PersonDto> personsDto = new ArrayList<>();
        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        for (String directoryPath : directoryPaths) {
            File dir = new File(directoryPath);
            File[] xmlFiles = dir.listFiles((d, name) -> name.endsWith(".xml"));
            if (xmlFiles != null) {
                for (File file : xmlFiles) {
                    Person person = (Person) unmarshaller.unmarshal(file);
                    PersonDto personDto = mapper.mapPersonToDto(person, determinePersonType(directoryPath));
                    personsDto.add(personDto);
                }
            }
        }
        return personsDto;
    }

    public boolean deleteXmlFile(String path) {
        if (path == null || !path.endsWith(".xml")) {
            System.out.println("Invalid file path or file is not an XML.");
            return false;
        }

        File file = new File(path);


        if (!file.exists()) {
            System.out.println("File does not exist: " + path);
            return false;
        }

        return file.delete();
    }


    public List<PersonFile> createPersonsFiles(List<String> directoryPaths) throws Exception {
        List<PersonFile> personsFiles = new ArrayList<>();
        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        for (String directoryPath : directoryPaths) {
            File dir = new File(directoryPath);
            File[] xmlFiles = dir.listFiles((d, name) -> name.endsWith(".xml"));
            if (xmlFiles != null) {
                for (File file : xmlFiles) {
                    Person person = (Person) unmarshaller.unmarshal(file);
                    personsFiles.add(new PersonFile( file.getPath(), person));
                }
            }
        }
        return personsFiles;
    }


    public List<PersonDto> readPersonsFromDirectory(String directoryPath) throws JAXBException {
        File dir = new File(directoryPath);
        File[] xmlFiles = dir.listFiles((d, name) -> name.endsWith(".xml"));
        List<PersonDto> persons = new ArrayList<>();

        if (xmlFiles == null) {
            return persons;
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        for (File file : xmlFiles) {
            Person person = (Person) unmarshaller.unmarshal(file);
            PersonDto personDto = mapper.mapPersonToDto(person, determinePersonType(directoryPath));
            persons.add(personDto);
        }

        return persons;
    }
}