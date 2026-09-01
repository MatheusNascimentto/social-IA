package com.nexel.socialai.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.nexel.socialai.user.dto.UpdateUserRequest;
import com.nexel.socialai.user.dto.UserResponse;
import com.nexel.socialai.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class UsersControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    void shouldReturnCurrentUser() throws Exception {
        when(userService.findByEmail("user@example.com")).thenReturn(com.nexel.socialai.user.entity.User.builder().email("user@example.com").fullName("Test User").build());

        mockMvc.perform(get("/users/me").principal(() -> "user@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCurrentUser() throws Exception {
        UpdateUserRequest req = new UpdateUserRequest("Updated Name", null);
        UserResponse updated = new UserResponse(java.util.UUID.randomUUID(), "Updated Name", "user@example.com");
        when(userService.updateProfile("user@example.com", req)).thenReturn(updated);

        mockMvc.perform(put("/users/me")
                        .principal(() -> "user@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"Updated Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName").value("Updated Name"));
    }
}
