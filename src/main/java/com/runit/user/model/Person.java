package com.runit.user.model;

import com.runit.user.util.ContractType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement(name="Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @XmlElement(name="personId")
    private String personId;

    @XmlElement(name="firstName")
    private String firstName;

    @XmlElement(name="lastName")
    private String lastName;

    @XmlElement(name="mobile")
    private String mobile;

    @XmlElement(name="email")
    private String email;

    @XmlElement(name="pesel")
    private String pesel;

    public Person(){}

    public Person(String firstName, String lastName, String mobile, String email, String pesel, ContractType type) {
        this.personId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

}
