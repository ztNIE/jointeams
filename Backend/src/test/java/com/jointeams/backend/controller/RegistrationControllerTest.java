package com.jointeams.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jointeams.backend.model.request.RegisterUserRequest;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService registerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser() throws Exception{
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("testRegister@uni.sydney.edu.au")
                .password("1234567")
                .universityId(1L)
                .degree("test degree")
                .faculty("test faculty")
                .build();
        mockMvc.perform(post("/register")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void verifyRegistration() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void savePassword() {
    }
}