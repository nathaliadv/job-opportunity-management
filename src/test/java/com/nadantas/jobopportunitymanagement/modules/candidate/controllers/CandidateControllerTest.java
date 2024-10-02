package com.nadantas.jobopportunitymanagement.modules.candidate.controllers;


import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.utils.BaseControllerTest;
import com.nadantas.jobopportunitymanagement.shared.Utils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class CandidateControllerTest extends BaseControllerTest {

    @Test
    public void shouldBeAbleToCreateANewCandidate() throws Exception {
       mvc.perform(
                MockMvcRequestBuilders.post("/candidate/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.objectToJSON(createCandidate("candidate_username", "candidate@email.com")))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Registration created successfully")));
    }

    @Test
    public void shouldReturnAnExceptionWhenTryToCreateACandidateAlreadyRegistered() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/candidate/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(Utils.objectToJSON(createCandidate("masantos", "maria-santos@gmail.com")))
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Username and/or email already registered.")));
    }

    private Candidate createCandidate(String username, String email) {
        return Candidate.builder()
                .id(UUID.randomUUID())
                .name("Candidate Name")
                .username(username)
                .email(email)
                .password("12345678abc")
                .description("Candidate Description Test")
                .build();
    }
}
