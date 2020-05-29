package co.uk.soletradercalculator.income_calculator.controllers;

import co.uk.soletradercalculator.income_calculator.fixture.Fixtures;
import co.uk.soletradercalculator.income_calculator.requests.CalculationsRequest;
import co.uk.soletradercalculator.income_calculator.services.FinanceCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FinanceCalculatorControllerTest extends AbstractJsonController {

    @Mock
    private FinanceCalculatorService financeCalculatorService;

    @InjectMocks
    private FinanceCalculatorController financeCalculatorController;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(financeCalculatorController).build();
    }

    @Test
    void getCalculationsForCurrentYear() throws Exception {

        CalculationsRequest request = new CalculationsRequest(29000,0, 0, false, 0);

        Mockito.when(financeCalculatorService.generateReport(request,2019)).thenReturn(Fixtures.getReport());



        mockMvc.perform(post("/api/v1/calculator/current").contentType(MediaType.APPLICATION_JSON).content(asJsonString(Fixtures.getReport())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    void getCalculationsForRequestedYear() throws Exception {

        CalculationsRequest request = new CalculationsRequest(29000,0, 0, false, 0);

        Mockito.when(financeCalculatorService.generateReport(request,2020)).thenReturn(Fixtures.getReport());

        mockMvc.perform(post("/api/v1/calculator/2020").contentType(MediaType.APPLICATION_JSON).content(asJsonString(Fixtures.getReport())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
