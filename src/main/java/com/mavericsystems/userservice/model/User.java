package com.mavericsystems.userservice.model;

import com.mavericsystems.userservice.enums.BloodGroup;
import com.mavericsystems.userservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String employeeNumber;
    private BloodGroup bloodGroup;
    private String password;
}
