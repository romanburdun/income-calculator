package co.uk.soletradercalculator.income_calculator.controllers;

import co.uk.soletradercalculator.income_calculator.services.TaxYearService;
import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxYearDTO;

import co.uk.soletradercalculator.income_calculator.fixture.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("dev")
class TaxYearControllerTest extends AbstractJsonController {

    @Mock
    private TaxYearService taxYearService;
    @InjectMocks
    private TaxYearController taxYearController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taxYearController).build();
    }

    @Test
    void saveTaxYear() throws Exception {

        Mockito.when(taxYearService.saveTaxYear(Mockito.any(TaxYearDTO.class))).thenReturn(Fixtures.getTaxYearDTO());

        mockMvc.perform(post("/api/v1/taxyear").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Fixtures.getTaxYearDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void getCurrentTaxYear() throws Exception{

        Mockito.when(taxYearService.getCurrentTaxYear()).thenReturn(Fixtures.getTaxYearDTO());

        mockMvc.perform(get("/api/v1/taxyear/current").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Fixtures.getTaxYearDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void getRequestedYear() throws Exception {

        Mockito.when(taxYearService.getRequestedTaxYear(2016)).thenReturn(Fixtures.getTaxYearDTO());

        mockMvc.perform(get("/api/v1/taxyear/2016").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Fixtures.getTaxYearDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


    }

    @Test
    void updateTaxYear() throws Exception {

        Mockito.when(taxYearService.updateTaxYear(2019, Fixtures.getTaxYearDTO())).thenReturn(Fixtures.getTaxYearDTO());

        mockMvc.perform(put("/api/v1/taxyear/2019").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Fixtures.getTaxYearDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


    }

    @Test
    void deleteTaxYear() throws Exception {

        Mockito.when(taxYearService.deleteTaxYear(2019)).thenReturn(Fixtures.getTaxYearDTO());

        mockMvc.perform(delete("/api/v1/taxyear/2019").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Fixtures.getTaxYearDTO())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


    }
}
