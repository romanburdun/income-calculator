package co.uk.soletradercalculator.income_calculator.services;

import co.uk.soletradercalculator.income_calculator.api.v1.mappers.StudentLoanMapper;
import co.uk.soletradercalculator.income_calculator.domain.NationalInsurance;
import co.uk.soletradercalculator.income_calculator.domain.StudentLoan;
import co.uk.soletradercalculator.income_calculator.domain.Tax;
import co.uk.soletradercalculator.income_calculator.domain.TaxYear;
import co.uk.soletradercalculator.income_calculator.fixture.Fixtures;
import co.uk.soletradercalculator.income_calculator.repositories.NationalInsuranceRepository;
import co.uk.soletradercalculator.income_calculator.repositories.StudentLoanRepository;
import co.uk.soletradercalculator.income_calculator.repositories.TaxRepository;
import co.uk.soletradercalculator.income_calculator.repositories.TaxYearRepository;
import co.uk.soletradercalculator.income_calculator.api.v1.mappers.NicMapper;
import co.uk.soletradercalculator.income_calculator.api.v1.mappers.TaxMapper;
import co.uk.soletradercalculator.income_calculator.api.v1.mappers.TaxYearMapper;
import co.uk.soletradercalculator.income_calculator.api.v1.model.TaxYearDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


class TaxYearServiceTest {


    private final TaxYearMapper  taxYearMapper= TaxYearMapper.INSTANCE;
    private final TaxMapper taxMapper= TaxMapper.INSTANCE;
    private final NicMapper nicMapper = NicMapper.INSTANCE;
    private final StudentLoanMapper studentLoanMapper = StudentLoanMapper.INSTANCE;
    @Mock
    private TaxYearRepository taxYearRepository;
    @Mock
    private TaxYearService taxYearService;

    @Mock
    private TaxRepository taxRepository;
    @Mock
    private NationalInsuranceRepository nationalInsuranceRepository;
    @Mock
    private StudentLoanRepository studentLoanRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        taxYearService = new TaxYearServiceImpl(
                taxYearRepository,
                taxRepository,
                nationalInsuranceRepository,
                studentLoanRepository,
                taxYearMapper,
                taxMapper,
                nicMapper,
                studentLoanMapper);
    }

    @Test
    void saveTaxYear() {

        Mockito.when(taxRepository.save(Mockito.any(Tax.class))).thenReturn(Fixtures.getTax());
        Mockito.when(nationalInsuranceRepository.save(Mockito.any(NationalInsurance.class))).thenReturn(Fixtures.getNationalInsurance());
        Mockito.when(studentLoanRepository.save(Mockito.any(StudentLoan.class))).thenReturn(Fixtures.getStudentLoan());
        Mockito.when(taxYearRepository.save(Mockito.any(TaxYear.class))).thenReturn(Fixtures.getTaxYear());

        TaxYearDTO taxYearDTO = taxYearService.saveTaxYear(taxYearMapper.taxYearToTaxYearDto(Fixtures.getTaxYear()));

        Assertions.assertNotNull(taxYearDTO);
        Assertions.assertEquals("06-04-2019", taxYearDTO.getTaxYearStarts());

    }

    @Test
    void getCurrentTaxYear() {

        Mockito.when(taxYearRepository.findByTaxYearStarts(Mockito.any(LocalDate.class))).thenReturn(Fixtures.getTaxYear());

        TaxYearDTO taxYearDTO = taxYearService.getCurrentTaxYear();

        Assertions.assertEquals("06-04-2019", taxYearDTO.getTaxYearStarts());

    }

    @Test
    void getRequestedTaxYear() {

        Mockito.when(taxYearRepository.findByTaxYearStarts(LocalDate.of(2017, 4, 6))).thenReturn(Fixtures.getTaxYear());

        TaxYearDTO taxYearDTO = taxYearService.getRequestedTaxYear(2017);

        Assertions.assertEquals("06-04-2019", taxYearDTO.getTaxYearStarts());
    }

    @Test
    void updateTaxYear() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate yearStarts = LocalDate.parse("06-04-2020", formatter);

        Tax updatedTax = new Tax();

        updatedTax.setPersonalAllowance(12900);
        updatedTax.setBasicThreshold(38700);
        updatedTax.setBasicRate(20);
        updatedTax.setHigherThreshold(152000);
        updatedTax.setHigherRate(40);
        updatedTax.setAdditionalRate(45);



        Mockito.when(taxYearRepository.findByTaxYearStarts(Mockito.any(LocalDate.class))).thenReturn(Fixtures.getTaxYear());

        Mockito.when(taxRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Fixtures.getTax()));
        Mockito.when(nationalInsuranceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Fixtures.getNationalInsurance()));
        Mockito.when(studentLoanRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Fixtures.getStudentLoan()));

        Mockito.when(taxRepository.save(Mockito.any(Tax.class))).thenReturn(updatedTax);
        Mockito.when(nationalInsuranceRepository.save(Mockito.any(NationalInsurance.class))).thenReturn(Fixtures.getNationalInsurance());
        Mockito.when(studentLoanRepository.save(Mockito.any(StudentLoan.class))).thenReturn(Fixtures.getStudentLoan());

        TaxYear updatedTaxYear = new TaxYear();
        updatedTaxYear.setTaxYearStarts(yearStarts);
        updatedTaxYear.setTax(updatedTax);
        updatedTaxYear.setNationalInsurance(Fixtures.getNationalInsurance());
        updatedTaxYear.setStudentLoan(Fixtures.getStudentLoan());
        Mockito.when(taxYearRepository.save(Mockito.any(TaxYear.class))).thenReturn(updatedTaxYear);


        TaxYearDTO updatedTYdto = taxYearService.updateTaxYear(2019, taxYearMapper.taxYearToTaxYearDto(updatedTaxYear));

        Assertions.assertEquals(12900, updatedTYdto.getTax().getPersonalAllowance());


    }

    @Test
    void deleteTaxYear() {


        Mockito.when(taxYearRepository.findByTaxYearStarts(Mockito.any(LocalDate.class))).thenReturn(Fixtures.getTaxYear());

        TaxYearDTO deletedTaxYear = taxYearService.deleteTaxYear(2020);

        Assertions.assertNotNull(deletedTaxYear);

    }
}
