package co.uk.soletradercalculator.income_calculator.controllers;

import co.uk.soletradercalculator.income_calculator.requests.CalculationsRequest;
import co.uk.soletradercalculator.income_calculator.services.FinanceCalculatorService;
import co.uk.soletradercalculator.income_calculator.api.v1.model.ReportDTO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/calculator")
@Api(tags={"Finance calculations controller"})
public class FinanceCalculatorController {

    private final FinanceCalculatorService financeCalculatorService;

    private static final int TAX_YEAR_STARTS_MONTH = 4;
    private static final int TAX_YEAR_STARTS_DATE = 6;

    @PostMapping("/current")
    public ReportDTO getCalculationsForCurrentYear(@RequestBody CalculationsRequest request) {


        LocalDate date = LocalDate.now();
        int year;
        if(date.getMonthValue() >= TAX_YEAR_STARTS_MONTH && date.getDayOfMonth() >= TAX_YEAR_STARTS_DATE) {
            year =  date.getYear();
        } else {
            year =  date.getYear() - 1;
        }


        return financeCalculatorService.generateReport(request, year);
    }

    @PostMapping("/{year}")
    public ReportDTO getCalculationsForRequestedYear(@PathVariable int year, @RequestBody CalculationsRequest request) {

        return financeCalculatorService.generateReport(request, year);
    }


}
