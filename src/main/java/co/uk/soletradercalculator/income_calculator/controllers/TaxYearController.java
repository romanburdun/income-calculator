package co.uk.soletradercalculator.income_calculator.controllers;

import co.uk.soletradercalculator.income_calculator.services.TaxYearService;
import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxYearDTO;
import co.uk.soletradercalculator.income_calculator.api.v1.model.YearsListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/taxyear")
@Api(tags={"Tax Year controller"}, authorizations = @Authorization(value = "Bearer"))
public class TaxYearController {



    private final TaxYearService taxYearService;


    @PostMapping
    @Authorization(value = "Bearer")
    public TaxYearDTO saveTaxYear(@RequestBody @Valid TaxYearDTO taxYearDTO) {

        TaxYearDTO savedTaxYear = taxYearService.saveTaxYear(taxYearDTO);

        return savedTaxYear;
    }




    @GetMapping("/current")
    public TaxYearDTO getCurrentTaxYear() {
        return taxYearService.getCurrentTaxYear();
    }

    @GetMapping("/{year}")
    public TaxYearDTO getRequestedYear(@PathVariable int year) {
        return taxYearService.getRequestedTaxYear(year);
    }


    @GetMapping("/list")
    public YearsListDTO getYearsList(HttpServletResponse response) {
        return taxYearService.getYearsList();
    }

    @PutMapping("/{year}")
    public TaxYearDTO updateTaxYear(@PathVariable int year, @RequestBody TaxYearDTO taxYearDTO) {
        return taxYearService.updateTaxYear(year, taxYearDTO);
    }


    @DeleteMapping("/{year}")
    public TaxYearDTO deleteTaxYear(@PathVariable int year) {
        return taxYearService.deleteTaxYear(year);
    }


}
