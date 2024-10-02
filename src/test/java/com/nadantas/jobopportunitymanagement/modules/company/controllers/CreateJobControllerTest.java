package com.nadantas.jobopportunitymanagement.modules.company.controllers;

import com.nadantas.jobopportunitymanagement.modules.company.dto.JobDTO;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Company;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.CompanyRepository;
import com.nadantas.jobopportunitymanagement.modules.utils.BaseControllerTest;
import com.nadantas.jobopportunitymanagement.modules.utils.TestUtils;
import com.nadantas.jobopportunitymanagement.shared.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateJobControllerTest extends BaseControllerTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void shouldBeAbleToCreateANewJob() throws Exception {
        Company company = Company.builder()
                .description("Company Description")
                .email("company_email@email.com")
                .password("dnediqwsiaj")
                .username("company_username")
                .website("www.companysite.com")
                .name("Company Name")
                .build();

        Company companySaved = companyRepository.saveAndFlush(company);

        mvc.perform(
                MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.objectToJSON(createJobDTO()))
                        .header("Authorization", TestUtils.generateTokenForCompany(companySaved.getId(), "company", "JAVAJOB_@123#"))
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
       try {
           mvc.perform(
                   MockMvcRequestBuilders.post("/company/job/")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(Utils.objectToJSON(createJobDTO()))
                           .header("Authorization", TestUtils.generateTokenForCompany(UUID.randomUUID(), "company", "JAVAJOB_@123#"))
           );
       } catch (Exception e) {
           assertTrue(e.getMessage() == "Company not found.");
       }
    }

    private JobDTO createJobDTO() {
        return JobDTO.builder()
                .description("Test description")
                .level("Junior")
                .benefits("Health insurance, Gym membership")
                .build();
    }
}
