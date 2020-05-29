package co.uk.soletradercalculator.income_calculator.services;

import co.uk.soletradercalculator.income_calculator.api.v1.model.ReportDTO;
import co.uk.soletradercalculator.income_calculator.fixture.Fixtures;
import co.uk.soletradercalculator.income_calculator.requests.CalculationsRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class FinanceCalculatorServiceTest {

    @Mock
    private TaxYearService taxYearService;
    @Mock
    private FinanceCalculatorServiceImpl financeCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        financeCalculatorService = new FinanceCalculatorServiceImpl(taxYearService);
    }

   @Test
    void generateReport() {

        CalculationsRequest request = new CalculationsRequest(29000,0, 2, false, 0);

        Mockito.when(taxYearService.getRequestedTaxYear(2019)).thenReturn(Fixtures.getTaxYearDTO());
        ReportDTO report = financeCalculatorService.generateReport(request, 2019);
        assertNotNull(report);
        assertEquals(29000, report.getSalary().getAnnual());

    }


}
