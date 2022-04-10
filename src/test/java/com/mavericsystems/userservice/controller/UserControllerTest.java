package com.mavericsystems.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavericsystems.userservice.constant.UserConstant;
import com.mavericsystems.userservice.dto.UserRequest;
import com.mavericsystems.userservice.dto.UserWithOutPassword;
import com.mavericsystems.userservice.enums.BloodGroup;
import com.mavericsystems.userservice.enums.Gender;
import com.mavericsystems.userservice.exception.UserIdMismatchException;
import com.mavericsystems.userservice.model.User;
import com.mavericsystems.userservice.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserWithOutPassword> userWithOutPassword = createUserList();
        Mockito.when(userService.getUsers(1, 2)).thenReturn(userWithOutPassword);
        mockMvc.perform(get("/users?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("firstTest")));
    }

    @Test
    void testGetUserByID() throws Exception {
        UserWithOutPassword UserWithOutPassword = createOneUser();
        Mockito.when(userService.getUserById("1")).thenReturn(UserWithOutPassword);
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(10)))
                .andExpect(jsonPath("$.firstName", Matchers.is("FirstID")));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        UserWithOutPassword UserWithOutPassword = createOneUser();
        Mockito.when(userService.getUserDetailsByEmail("prabhu@mail.com")).thenReturn(UserWithOutPassword);
        mockMvc.perform(get("/users/getUserByEmail/prabhu@mail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(10)))
                .andExpect(jsonPath("$.firstName", Matchers.is("FirstID")));
    }

    @Test
    void testDeleteUserById() throws Exception {
        Mockito.when(userService.deleteUser("1")).thenReturn(UserConstant.DELETE_USER);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void testCreateUser() throws Exception {
        User user = createOneUserToPost();
        UserWithOutPassword UserWithOutPassword = createUserWithOutPassword();
        UserRequest userRequest = new UserRequest();
        Mockito.when(userService.createUser(userRequest)).thenReturn(UserWithOutPassword);
        mockMvc.perform(post("/users")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUserById() throws Exception {
        User user = createOneUserToPost();
        UserWithOutPassword UserWithOutPassword = new UserWithOutPassword();
        UserRequest userRequest = new UserRequest();
        Mockito.when(userService.updateUser(userRequest, "1")).thenReturn(UserWithOutPassword);
        mockMvc.perform(put("/users/1")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testExceptionThrownWhenUserIdNotFound() throws Exception {
        UserController userController = new UserController();
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getId()).thenReturn("a");
        assertThrows(UserIdMismatchException.class, () -> userController.updateUser(userRequest, "1"));
        verify(userRequest).getId();
    }

    private List<UserWithOutPassword> createUserList() {
        List<UserWithOutPassword> users = new ArrayList<>();

        UserWithOutPassword user1 = new UserWithOutPassword();
        user1.setId("A123A");
        user1.setFirstName("firstTest");
        user1.setMiddleName("J");
        user1.setLastName("S");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("prabhu@mail.com");
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup(BloodGroup.O_POS);
        user1.setGender(Gender.MALE);

        UserWithOutPassword user2 = new UserWithOutPassword();
        user2.setId("A123A");
        user2.setFirstName("secondTest");
        user2.setMiddleName("J");
        user2.setLastName("S");
        user2.setPhoneNumber("9090909090");
        user2.setEmail("prabhu@mail.com");
        user2.setEmployeeNumber("12345");
        user2.setBloodGroup(BloodGroup.O_POS);
        user2.setGender(Gender.MALE);

        users.add(user1);
        users.add(user2);
        return users;
    }


    private UserWithOutPassword createOneUser() {
        UserWithOutPassword userWithOutPassword = new UserWithOutPassword();
        userWithOutPassword.setId("1");
        userWithOutPassword.setFirstName("FirstID");
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


    private UserWithOutPassword createUserWithOutPassword() {
        UserWithOutPassword user = new UserWithOutPassword();
        user.setId("1");
        user.setFirstName("Prabhu");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        return user;
    }

    private User createOneUserToPost() {
        User user = new User();
        user.setId("1");
        user.setFirstName("Prabhu");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("12345");
        return user;
    }
}
