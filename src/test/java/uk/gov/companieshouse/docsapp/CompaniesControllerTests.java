package uk.gov.companieshouse.docsapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import uk.gov.companieshouse.docsapp.dao.InMemoryCompanyRegistry;
import uk.gov.companieshouse.docsapp.model.company.Company;
import uk.gov.companieshouse.docsapp.model.company.LimitedCompany;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CompaniesControllerTests {

    InMemoryCompanyRegistry inMemoryCompanyRegistry = new InMemoryCompanyRegistry();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void tearDown() {

    }
    @Test
    @DisplayName("list Companies should return all companies")
    void listCompaniesShouldReturnAllCompanies() throws Exception {
        this.mockMvc.perform(get("/companies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"type\":\"LTD\",\"companyName\":\"We Are Partners LLP\",\"active\":true,\"registrationNumber\":\"194745294\",\"registeredAddress\":null,\"incorporatedOn\":\"2023-09-01\",\"numberOfShares\":0,\"public\":false},{\"type\":\"LTD\",\"companyName\":\"Italian Company Ltd\",\"active\":true,\"registrationNumber\":\"637399827\",\"registeredAddress\":null,\"incorporatedOn\":\"2008-01-05\",\"numberOfShares\":0,\"public\":false},{\"type\":\"LTD\",\"companyName\":\"My Company Ltd\",\"active\":true,\"registrationNumber\":\"123456789\",\"registeredAddress\":null,\"incorporatedOn\":\"2010-04-20\",\"numberOfShares\":0,\"public\":false},{\"type\":\"LTD\",\"companyName\":\"No Profit Ltd\",\"active\":true,\"registrationNumber\":\"946401763\",\"registeredAddress\":null,\"incorporatedOn\":\"2003-03-13\",\"numberOfShares\":0,\"public\":false}]"))
                .andReturn();
    }

    @Test
    @DisplayName("Get using registration number returns correct company")
    void getSpecificCompanyReturnsCorrectCompany() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/companies/123456789"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Company company = mapper.readValue(result.getResponse().getContentAsString(), Company.class);
        assertThat(company.getCompanyName()).isEqualTo("My Company Ltd");
        assertThat(company.getRegistrationNumber()).isEqualTo("123456789");
        assertThat(company.getIncorporatedOn()).isEqualTo("2010-04-20");
        assertThat(company.isActive()).isEqualTo(true);
    }

    @Test
    void deleteCompany () throws Exception {
        ResultActions result = this.mockMvc.perform(delete("/companies/78186716"));
    }

    @Test
    void postCorrectlyAddsCompany() throws Exception {
        LimitedCompany newCompany = new LimitedCompany("Vico Ltd",true);
        String json = mapper.writeValueAsString(newCompany);
        MvcResult result = this.mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        Company returnedCompany = mapper.readValue(result.getResponse().getContentAsString(), Company.class);

        assertEquals(newCompany.getCompanyName(), returnedCompany.getCompanyName());
        assertEquals(newCompany.isActive(), returnedCompany.isActive());
    }

    @Test
    void putCorrectlyEditsCompany() throws Exception {
        MvcResult result = this.mockMvc.perform(put("/companies/123456789"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}
