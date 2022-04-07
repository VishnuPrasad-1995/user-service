package com.mavericsystems.userservice.service;

import com.mavericsystems.userservice.constant.UserConstant;
import com.mavericsystems.userservice.dto.UserRequest;
import com.mavericsystems.userservice.dto.UserWithOutPassword;
import com.mavericsystems.userservice.enums.BloodGroup;
import com.mavericsystems.userservice.enums.Gender;
import com.mavericsystems.userservice.exception.EmailAlreadyExistException;
import com.mavericsystems.userservice.exception.UserNotFoundException;
import com.mavericsystems.userservice.model.User;
import com.mavericsystems.userservice.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @InjectMocks
    UserServiceImpl service;

    @Mock
    UserRepo userRepo;


    @Test
    void testCreateUser() {
        UserWithOutPassword userWithOutPassword = createOneUserToResponse();
        UserRequest userRequest = createOneUserRequestToPost();
        User user = new User();
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setDateOfBirth(LocalDate.of(1997, 03, 23));
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        when(userRepo.save(any(User.class))).thenReturn(user);
        User savedUser = userRepo.save(user);
        when(service.createUser(userRequest)).thenReturn(userWithOutPassword);
        assertThat(savedUser.getFirstName()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("prabhu@mail.com");
    }

    @Test
    void testExceptionThrownWhenEmailAlreadyRegistered() {
        User user = createOneUserToCheck();
        User userToUpdate = createOneUserToUpdate();
        UserRequest userRequest = new UserRequest();
        Optional<User> ofResult = Optional.of(userToUpdate);
        when(this.userRepo.save((User) any())).thenReturn(user);
        when(this.userRepo.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(EmailAlreadyExistException.class, () -> this.service.createUser(userRequest));
        verify(this.userRepo).findByEmail((String) any());
    }

    @Test
    void testDeleteUserById() {
        service.deleteUser("1");
        verify(userRepo, times(1)).deleteById("1");
    }

    @Test
    void testExceptionThrownWhenUserNotFound() {
        Mockito.doThrow(UserNotFoundException.class).when(userRepo).deleteById(any());
        Exception userNotFoundException = assertThrows(UserNotFoundException.class, () -> service.deleteUser("1"));
        assertTrue(userNotFoundException.getMessage().contains(UserConstant.USER_NOT_FOUND));
    }

    @Test
    void testExceptionThrownWhenUserIdNotFound() {
        User user = createOneUserToUpdate();
        UserWithOutPassword UserWithOutPassword = createOneUserToResponse();
        UserRequest userRequest = createOneUserRequestToPost();
        Mockito.when(userRepo.findById("1")).thenReturn(Optional.ofNullable(user));
        assertThat(service.updateUser(userRequest, "1")).isEqualTo(UserWithOutPassword);
        assertThrows(UserNotFoundException.class, () -> service.updateUser(userRequest, null));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setDateOfBirth(LocalDate.of(1997, 03, 23));
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        UserWithOutPassword UserWithOutPassword = createOneUserToResponse();
        Mockito.when(userRepo.findById("1")).thenReturn(Optional.ofNullable(user));
        assertThat(service.getUserById(user.getId())).isEqualTo(UserWithOutPassword);
        assertThrows(UserNotFoundException.class, () -> service.getUserById(null));
    }

    @Test
    void testGetUserDetailsByEmail() {
        User user = new User();
        user.setId("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setDateOfBirth(LocalDate.of(1997, 03, 23));
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        UserWithOutPassword UserWithOutPassword = createOneUserToResponse();
        Mockito.when(userRepo.findByEmail("prabhu@mail.com")).thenReturn(Optional.ofNullable(user));
        assertThat(service.getUserDetailsByEmail(user.getEmail())).isEqualTo(UserWithOutPassword);
        assertThrows(UserNotFoundException.class, () -> service.getUserDetailsByEmail(null));
    }

    @Test
    void testGetAllUsers() {
        User user = createOneUserToCheck();
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        PageImpl<User> pageImpl = new PageImpl<>(users);

        when(this.userRepo.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        assertEquals(1, this.service.getUsers(1, 3).size());
        verify(this.userRepo).findAll((org.springframework.data.domain.Pageable) any());
    }

    private UserWithOutPassword createOneUserToResponse() {
        UserWithOutPassword userWithOutPassword = new UserWithOutPassword();
        userWithOutPassword.setId("1");
        userWithOutPassword.setFirstName("firstTest");
        userWithOutPassword.setMiddleName("J");
        userWithOutPassword.setLastName("S");
        userWithOutPassword.setPhoneNumber("9090909090");
        userWithOutPassword.setEmail("prabhu@mail.com");
        userWithOutPassword.setDateOfBirth(LocalDate.of(1997, 03, 23));
        userWithOutPassword.setEmployeeNumber("12345");
        userWithOutPassword.setBloodGroup(BloodGroup.O_POS);
        userWithOutPassword.setGender(Gender.MALE);
        return userWithOutPassword;
    }

    private UserRequest createOneUserRequestToPost() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("firstTest");
        userRequest.setMiddleName("J");
        userRequest.setLastName("S");
        userRequest.setPhoneNumber("9090909090");
        userRequest.setEmail("prabhu@mail.com");
        userRequest.setDateOfBirth(LocalDate.of(1997, 03, 23));
        userRequest.setEmployeeNumber("12345");
        userRequest.setBloodGroup(BloodGroup.O_POS);
        userRequest.setGender(Gender.MALE);
        userRequest.setPassword("12345");
        return userRequest;
    }

    private User createOneUserToUpdate() {
        User user = new User();
        user.setId("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setDateOfBirth(LocalDate.of(1997, 03, 23));
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");
        return user;
    }

    private User createOneUserToCheck() {
        User user = new User();
        user.setId("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setDateOfBirth(LocalDate.of(1997, 03, 23));
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");
        return user;
    }


    private List<User> createUsersList1() {
        List<User> users = new ArrayList<>();
        User UserWithOutPassword1 = new User();
        UserWithOutPassword1.setId("1");
        UserWithOutPassword1.setFirstName("firstTest");
        UserWithOutPassword1.setMiddleName("J");
        UserWithOutPassword1.setLastName("S");
        UserWithOutPassword1.setPhoneNumber("9090909090");
        UserWithOutPassword1.setEmail("prabhu@mail.com");
        UserWithOutPassword1.setDateOfBirth(LocalDate.of(1997, 03, 23));
        UserWithOutPassword1.setEmployeeNumber("12345");
        UserWithOutPassword1.setBloodGroup(BloodGroup.O_POS);
        UserWithOutPassword1.setGender(Gender.MALE);

        User UserWithOutPassword2 = new User();
        UserWithOutPassword2.setId("2");
        UserWithOutPassword2.setFirstName("secondTest");
        UserWithOutPassword2.setMiddleName("J");
        UserWithOutPassword2.setLastName("S");
        UserWithOutPassword2.setPhoneNumber("9090909090");
        UserWithOutPassword2.setEmail("prabhu@mail.com");
        UserWithOutPassword2.setDateOfBirth(LocalDate.of(1997, 03, 23));
        UserWithOutPassword2.setEmployeeNumber("12345");
        UserWithOutPassword2.setBloodGroup(BloodGroup.O_POS);
        UserWithOutPassword2.setGender(Gender.MALE);

        users.add(UserWithOutPassword1);
        users.add(UserWithOutPassword2);
        return users;
    }

    private List<UserWithOutPassword> createUsersList() {
        List<UserWithOutPassword> userWithOutPasswordList = new ArrayList<>();
        UserWithOutPassword userWithOutPassword = new UserWithOutPassword();
        userWithOutPassword.setId("623e238c0f3e41623a004497");
        userWithOutPassword.setFirstName("Vishnu");
        userWithOutPassword.setMiddleName("J");
        userWithOutPassword.setLastName("S");
        userWithOutPassword.setPhoneNumber("9846160222");
        userWithOutPassword.setEmail("vishnuprasad@mail.com");
        userWithOutPassword.setDateOfBirth(LocalDate.of(1997, 03, 23));
        userWithOutPassword.setEmployeeNumber("7048");
        userWithOutPassword.setBloodGroup(BloodGroup.O_POS);
        userWithOutPassword.setGender(Gender.MALE);

        userWithOutPasswordList.add(userWithOutPassword);
        return userWithOutPasswordList;
    }
}