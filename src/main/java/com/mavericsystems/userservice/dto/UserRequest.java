package com.mavericsystems.userservice.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.mavericsystems.userservice.enums.BloodGroup;
import com.mavericsystems.userservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "First name must not be blank")
    @NotNull(message = "First name should not null")
    private String firstName;
    @NotBlank(message = "Last name must not be blank")
    @NotNull(message = "Last name should not null")
    private String lastName;
    @NotBlank(message = "Middle name must not be blank")
    @NotNull(message = "Middle name should not null")
    private String middleName;
    @NotBlank(message = "Phone number must not be blank")
    @NotNull(message = "Phone number should not null")
    @Size(min = 10, max = 10, message = "phone number should be a 10 digit number")
    private String phoneNumber;
    @NotBlank(message = "email should not be blank")
    @NotNull(message = "email should not be null")
    @Email(message = "Please enter a valid email address")
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Gender gender;
    @NotBlank(message = "Employee num should not be blank")
    @NotNull(message = "Employee num should not be null")
    private String employeeNumber;
    private BloodGroup bloodGroup;
    @NotEmpty(message = "password should not be empty")
    @Size(min = 4, message = "password should have at least 8 characters")
    private String password;

}

