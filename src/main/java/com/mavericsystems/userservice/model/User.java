package com.mavericsystems.userservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Document(collection = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @NotBlank(message = "First name must not be blank")
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;
    @NotBlank
    @NotNull
    private String middleName;
    @NotBlank
    @NotNull
    private String phoneNumber;
    @NotBlank
    @NotNull
    private String email;
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @NotBlank
    @NotNull
    private String gender;
    private String maritalStatus;
    @NotBlank
    @NotNull
    private String employeeNumber;
    @NotBlank
    @NotNull
    private String bloodGroup;
    @NotBlank
    @NotNull
    private String password;

}
