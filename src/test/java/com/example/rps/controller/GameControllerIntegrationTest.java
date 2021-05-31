package com.example.rps.controller;

import com.example.rps.RpsApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.platform.engine.TestExecutionResult.failed;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RpsApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GameControllerIntegrationTest {
    @Value("classpath:single_user_one_round.json")
    private Resource responseSingleUserOneRound;
    @Value("classpath:single_user_two_rounds.json")
    private Resource responseSingleUserTwoRounds;
    @Value("classpath:multi_users.json")
    private Resource responseMultiUsers;
    @Value("classpath:empty.json")
    private Resource responseEmpty;
    @Value("classpath:error400.json")
    private Resource responseError400;
    @Value("classpath:error500.json")
    private Resource responseError500;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Random random;

    private final MockHttpSession mockSession1 = new MockHttpSession();
    private final MockHttpSession mockSession2 = new MockHttpSession();

    @BeforeEach
    public void setUp() {
        when(random.nextInt(anyInt())).thenReturn(1);
    }

    @Test
    void shouldPlayRound() throws Exception {
        mockMvc.perform(post("/api/games/new")
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/games/1"))
                .andExpect(content().string(extract(responseSingleUserOneRound)));
    }

    @Test
    void shouldPlayTwoRounds() throws Exception {
        mockMvc.perform(post("/api/games/new").session(mockSession1));
        mockMvc.perform(post("/api/games/new").session(mockSession1));

        mockMvc.perform(get("/api/games/all").session(mockSession1)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(extract(responseSingleUserTwoRounds)));
    }

    @Test
    void shouldPlayMultiUser() throws Exception {
        mockMvc.perform(post("/api/games/new").session(mockSession1));
        mockMvc.perform(post("/api/games/new").session(mockSession2));

        mockMvc.perform(get("/api/games/all?AllUsers=true")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(extract(responseMultiUsers)));
    }

    @Test
    void shouldRemoveUserRounds() throws Exception {
        mockMvc.perform(post("/api/games/new").session(mockSession1));
        mockMvc.perform(post("/api/games/new").session(mockSession1));

        mockMvc.perform(delete("/api/games/all").session(mockSession1)
                .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/games/all").session(mockSession1)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(extract(responseEmpty)));
    }

    @Test
    void shouldRaise400() throws Exception {
        mockMvc.perform(get("/api/games/1W"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(extract(responseError400)));
    }

    @Test
    void shouldRaise500() throws Exception {
        doThrow(NullPointerException.class).when(random).nextInt(anyInt());
        mockMvc.perform(post("/api/games/new"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(extract(responseError500)));
    }

    private String extract(final Resource resource) {
        String json = "";
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            json = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            failed(null);
        }

        return json;
    }

}
