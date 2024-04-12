package com.runit.user.service;

import com.runit.user.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class FillAndValidate {
    public String validatePersonDto(PersonDto personDto) {
        if (personDto.getFirstName() == null ||
                personDto.getLastName() == null || personDto.getMobile() == null ||
                personDto.getEmail() == null || personDto.getPesel() == null) {
            return "All fields must be provided and cannot be null.";
        }
        return null;
    }
}
