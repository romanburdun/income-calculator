package co.uk.soletradercalculator.income_calculator.services;

import co.uk.soletradercalculator.income_calculator.requests.CalculationsRequest;
import co.uk.soletradercalculator.income_calculator.api.v1.model.ReportDTO;

public interface FinanceCalculatorService {
    ReportDTO generateReport(CalculationsRequest request, int year);

}
