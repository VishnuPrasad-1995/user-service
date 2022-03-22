package com.mavericsystems.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
   @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String employeeNumber;
    private String bloodGroup;
    private String gender;
}
