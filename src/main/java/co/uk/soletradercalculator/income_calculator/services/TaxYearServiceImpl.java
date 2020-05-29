package co.uk.soletradercalculator.income_calculator.services;

import co.uk.soletradercalculator.income_calculator.api.v1.mappers.StudentLoanMapper;
import co.uk.soletradercalculator.income_calculator.api.v1.model.*;
import co.uk.soletradercalculator.income_calculator.domain.NationalInsurance;
import co.uk.soletradercalculator.income_calculator.domain.StudentLoan;
import co.uk.soletradercalculator.income_calculator.domain.Tax;
import co.uk.soletradercalculator.income_calculator.domain.TaxYear;
import co.uk.soletradercalculator.income_calculator.repositories.TaxYearRepository;
import co.uk.soletradercalculator.income_calculator.api.v1.mappers.NicMapper;
import co.uk.soletradercalculator.income_calculator.api.v1.mappers.TaxMapper;
import co.uk.soletradercalculator.income_calculator.api.v1.mappers.TaxYearMapper;
import co.uk.soletradercalculator.income_calculator.exceptions.ApiRequestException;
import co.uk.soletradercalculator.income_calculator.repositories.NationalInsuranceRepository;
import co.uk.soletradercalculator.income_calculator.repositories.StudentLoanRepository;
import co.uk.soletradercalculator.income_calculator.repositories.TaxRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaxYearServiceImpl implements TaxYearService {

    private static int TAX_YEAR_START_DATE = 6;
    private static int TAX_YEAR_START_MONTH = 4;

    private final TaxYearRepository taxYearRepository;
    private final TaxRepository taxRepository;
    private final NationalInsuranceRepository nationalInsuranceRepository;
    private final StudentLoanRepository studentLoanRepository;

    private final TaxYearMapper taxYearMapper;
    private final TaxMapper taxMapper;
    private final NicMapper nicMapper;
    private final StudentLoanMapper studentLoanMapper;

    @Override
    public TaxYearDTO saveTaxYear(TaxYearDTO taxYearDTO) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate yearStarts = LocalDate.parse(taxYearDTO.getTaxYearStarts(), formatter);

        if(taxYearRepository.existsByTaxYearStarts(yearStarts)) {
            throw new ApiRequestException("Tax year already exists", HttpStatus.CONFLICT);
        }

        if(taxYearDTO.getTaxYearStarts() == null || taxYearDTO.getTaxYearStarts() == "") {
            throw new ApiRequestException("Tax year start date is missing", HttpStatus.BAD_REQUEST);
        }

        if(taxYearDTO.getTax() == null) {
            throw new ApiRequestException("Tax year information is missing!", HttpStatus.BAD_REQUEST);
        }

        if(taxYearDTO.getNationalInsurance() == null) {
            throw new ApiRequestException("National insurance information is missing!", HttpStatus.BAD_REQUEST);
        }

        if(taxYearDTO.getStudentLoan() == null) {
            throw new ApiRequestException("Student loan information is missing!", HttpStatus.BAD_REQUEST);
        }



        TaxDTO taxDTO = taxYearDTO.getTax();
        taxDTO.setTaxYearStarts(taxYearDTO.getTaxYearStarts());
        Tax tax = taxRepository.save(taxMapper.taxDtoToTax(taxDTO));

        NationalInsuranceDTO nicDTO = taxYearDTO.getNationalInsurance();
        nicDTO.setTaxYearStarts(taxYearDTO.getTaxYearStarts());
        NationalInsurance nic = nationalInsuranceRepository.save(nicMapper.nicDtoToNic(nicDTO));

        StudentLoanDTO slDTO = taxYearDTO.getStudentLoan();
        slDTO.setTaxYearStarts(taxYearDTO.getTaxYearStarts());
        StudentLoan studentLoan = studentLoanRepository.save(studentLoanMapper.studentLoanDtoToStudentLoan(slDTO));

        TaxYear newTaxYear = new TaxYear();
        newTaxYear.setTaxYearStarts(yearStarts);
        newTaxYear.setTax(tax);
        newTaxYear.setNationalInsurance(nic);
        newTaxYear.setStudentLoan(studentLoan);

        TaxYear taxYear = taxYearRepository.save(newTaxYear);

        return taxYearMapper.taxYearToTaxYearDto(taxYear);
    }

    @Override
    public TaxYearDTO updateTaxYear(int taxYear,TaxYearDTO taxYearDTO) {

        LocalDate yearStarts = LocalDate.of(taxYear,TAX_YEAR_START_MONTH, TAX_YEAR_START_DATE);


        if(taxYearDTO.getTax() == null) {
            throw new ApiRequestException("Tax year information is missing!", HttpStatus.BAD_REQUEST);
        }

        if(taxYearDTO.getNationalInsurance() == null) {
            throw new ApiRequestException("National insurance information is missing!", HttpStatus.BAD_REQUEST);
        }

        if(taxYearDTO.getStudentLoan() == null) {
            throw new ApiRequestException("Student loan information is missing!", HttpStatus.BAD_REQUEST);
        }

        TaxYear taxYearSearch = taxYearRepository.findByTaxYearStarts(yearStarts);

        if(taxYearSearch == null) {
            throw new ApiRequestException("Requested tax year not found", HttpStatus.NOT_FOUND);
        }

        updateTaxInfo(taxYearSearch, taxYearDTO.getTax());

        updateNationalInsuranceInfo(taxYearSearch, taxYearDTO.getNationalInsurance());

        updateStudentLoanInfo(taxYearSearch, taxYearDTO.getStudentLoan());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate yearStartsUpdate = LocalDate.parse(taxYearDTO.getTaxYearStarts(), formatter);


        taxYearSearch.setTaxYearStarts(yearStartsUpdate);

        return taxYearMapper.taxYearToTaxYearDto(taxYearRepository.save(taxYearSearch));
    }

    @Override
    public TaxYearDTO deleteTaxYear(int taxYear) {

        LocalDate yearStarts = LocalDate.of(taxYear, TAX_YEAR_START_MONTH, TAX_YEAR_START_DATE);

        TaxYear taxYearSearch =taxYearRepository.findByTaxYearStarts(yearStarts);

        if (taxYearSearch == null) {
            throw new ApiRequestException("Requested tax year not found", HttpStatus.NOT_FOUND);
        }

         taxYearRepository.delete(taxYearSearch);

        return taxYearMapper.taxYearToTaxYearDto(taxYearSearch);
    }


    @Override
    public TaxYearDTO getCurrentTaxYear() {



        LocalDate date = LocalDate.now();
        LocalDate requestDate;
        if(date.getMonthValue() >= TAX_YEAR_START_MONTH && date.getDayOfMonth() >= TAX_YEAR_START_DATE) {
            requestDate = LocalDate.of(date.getYear(), TAX_YEAR_START_MONTH, TAX_YEAR_START_DATE);
        } else {
            requestDate = LocalDate.of(date.getYear() - 1, TAX_YEAR_START_MONTH, TAX_YEAR_START_DATE);
        }

        TaxYear currentYear = taxYearRepository.findByTaxYearStarts(requestDate);

        if(currentYear == null) {
             throw new ApiRequestException("Requested tax year not found", HttpStatus.NOT_FOUND);
        }

        return taxYearMapper.taxYearToTaxYearDto(currentYear);
    }

    @Override
    public TaxYearDTO getRequestedTaxYear(int taxYear) {

       TaxYear requestedYear = taxYearRepository.findByTaxYearStarts(LocalDate.of(taxYear, TAX_YEAR_START_MONTH, TAX_YEAR_START_DATE));

        if (requestedYear == null) {
            throw new ApiRequestException("Requested tax year not found", HttpStatus.NOT_FOUND);
        }
            return taxYearMapper.taxYearToTaxYearDto(requestedYear);
    }

    @Override
    public YearsListDTO getYearsList() {

        List<TaxYear> years = taxYearRepository.findAllByOrderByTaxYearStartsDesc();

        List<Integer> yearsList = new ArrayList<>();
        for ( TaxYear year : years) {
            yearsList.add(year.getTaxYearStarts().getYear());
        }

        YearsListDTO list = new YearsListDTO();
       list.setYearsList(yearsList);
        return list;
    }


    //Helper methods
    private void updateStudentLoanInfo(TaxYear taxYearSearch, StudentLoanDTO studentLoanDTO) {
        Optional<StudentLoan> studentLoanOptional = studentLoanRepository.findById(taxYearSearch.getStudentLoan().getId());

        if(studentLoanOptional.isPresent()) {
            StudentLoan updateStudentLoan = studentLoanOptional.get();
            updateStudentLoan.setPlanOneAnnualThreshold(studentLoanDTO.getPlanOneAnnualThreshold());
            updateStudentLoan.setPlanOneRate(studentLoanDTO.getPlanOneRate());
            updateStudentLoan.setPlanTwoAnnualThreshold(studentLoanDTO.getPlanTwoAnnualThreshold());
            updateStudentLoan.setPlanTwoRate(studentLoanDTO.getPlanTwoRate());
            studentLoanRepository.save(updateStudentLoan);
        } else {
            throw new ApiRequestException("Student loan information for requested year not found", HttpStatus.NOT_FOUND);
        }
    }

    private void updateTaxInfo(TaxYear taxYearSearch, TaxDTO newTaxInfo) {
        Optional<Tax> taxOptional = taxRepository.findById(taxYearSearch.getTax().getId());

        if(taxOptional.isPresent()) {
            Tax updateTax = taxOptional.get();
            updateTax.setPersonalAllowance(newTaxInfo.getPersonalAllowance());
            updateTax.setBasicThreshold(newTaxInfo.getBasicThreshold());
            updateTax.setBasicRate(newTaxInfo.getBasicRate());
            updateTax.setHigherThreshold(newTaxInfo.getHigherThreshold());
            updateTax.setHigherRate(newTaxInfo.getHigherRate());
            updateTax.setAdditionalRate(newTaxInfo.getAdditionalRate());
            taxRepository.save(updateTax);
        } else {
            throw new ApiRequestException("Tax information for requested year not found", HttpStatus.NOT_FOUND);
        }
    }

    private void updateNationalInsuranceInfo(TaxYear taxYearSearch, NationalInsuranceDTO nicDTO) {
        Optional<NationalInsurance> nicOptional = nationalInsuranceRepository.findById(taxYearSearch.getNationalInsurance().getId());

        if(nicOptional.isPresent()) {
            NationalInsurance nicUpdate = nicOptional.get();
            nicUpdate.setClassTwo(nicDTO.getClassTwo());
            nicUpdate.setLowerProfitsLimit(nicDTO.getLowerProfitsLimit());
            nicUpdate.setLowerProfitsLimitRate(nicDTO.getLowerProfitsLimitRate());
            nicUpdate.setUpperProfitsLimit(nicDTO.getUpperProfitsLimit());
            nicUpdate.setUpperProfitsLimitRate(nicDTO.getUpperProfitsLimitRate());
            nationalInsuranceRepository.save(nicUpdate);
        } else {
            throw new ApiRequestException("National insurance information for requested year not found", HttpStatus.NOT_FOUND);
        }
    }

}

