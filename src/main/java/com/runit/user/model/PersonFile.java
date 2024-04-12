package com.runit.user.model;

public class PersonFile {
    private String fileName;
    private Person person;

    public PersonFile(String fileName, Person person) {
        this.fileName = fileName;
        this.person = person;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}