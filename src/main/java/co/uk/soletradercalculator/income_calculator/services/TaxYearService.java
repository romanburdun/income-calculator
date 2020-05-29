package co.uk.soletradercalculator.income_calculator.services;

import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxYearDTO;
import co.uk.soletradercalculator.income_calculator.api.v1.model.YearsListDTO;

public interface TaxYearService {
    TaxYearDTO saveTaxYear(TaxYearDTO taxYearDTO);
    TaxYearDTO updateTaxYear(int taxYear,TaxYearDTO taxYearDTO);
    TaxYearDTO deleteTaxYear(int taxYear);
    TaxYearDTO getCurrentTaxYear();
    TaxYearDTO getRequestedTaxYear(int taxYear);
    YearsListDTO getYearsList();
}
