package com.mavericsystems.userservice.dto;

import com.mavericsystems.userservice.enums.BloodGroup;
import com.mavericsystems.userservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String employeeNumber;
    private BloodGroup bloodGroup;
    private Gender gender;
}
