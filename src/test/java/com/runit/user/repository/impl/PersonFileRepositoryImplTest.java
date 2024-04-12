package com.runit.user.repository.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.runit.user.dto.PersonDto;
import com.runit.user.model.Person;
import com.runit.user.model.PersonFile;
import com.runit.user.util.ContractType;
import com.runit.user.util.XmlReader;
import com.runit.user.util.XmlSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PersonFileRepositoryImplTest {

    @Mock
    private XmlReader xmlReader;

    @InjectMocks
    private PersonFileRepositoryImpl classUnderTest;

    private Person person;
    private PersonDto personDto;
    private List<String> files;

    @BeforeEach
    void setUp() {
        person = new Person("John", "Doe", "1234567890", "john@example.com", "12345678901", ContractType.INTERNAL);
        personDto = new PersonDto();
        files = Arrays.asList("src/main/resources/static/Internal", "src/main/resources/static/External");
    }

    @Test
    void testReadAllPersons() throws Exception {
        when(xmlReader.readPersonsFromXmlDirectories(anyList())).thenReturn(new ArrayList<>());
        assertTrue(classUnderTest.readAllPersons().isEmpty());
    }

    @Test
    void testFindByContractTypeInternal() throws Exception {
        when(xmlReader.readPersonsFromDirectory(anyString())).thenReturn(new ArrayList<>());
        assertTrue(classUnderTest.findByContractType(ContractType.INTERNAL).isEmpty());
    }

    @Test
    void testSavePerson() {
        assertDoesNotThrow(() -> classUnderTest.savePerson(ContractType.INTERNAL, personDto));
    }

    @Test
    void testDeletePerson() throws Exception {
        when(xmlReader.createPersonsFiles(files)).thenReturn(List.of(new PersonFile("some/path/file.xml", person)));
        when(xmlReader.deleteXmlFile(anyString())).thenReturn(true);
        assertTrue(classUnderTest.deletePerson(person.getPersonId()));
    }


    @Test
    void testUpdatePerson() throws Exception {
        when(xmlReader.createPersonsFiles(files)).thenReturn(List.of(new PersonFile("some/path/file.xml", person)));
        Person updatedPerson = new Person("Jane", "Doe", "0987654321", "jane@example.com", "98765432100", ContractType.INTERNAL);
        assertDoesNotThrow(() -> classUnderTest.updatePerson(person.getPersonId(), person, updatedPerson));
    }
}
