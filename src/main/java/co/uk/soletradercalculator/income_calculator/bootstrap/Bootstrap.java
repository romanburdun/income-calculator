package co.uk.soletradercalculator.income_calculator.bootstrap;


import co.uk.soletradercalculator.income_calculator.domain.*;
import co.uk.soletradercalculator.income_calculator.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Profile("dev")
@Component
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final TaxYearRepository taxYearRepository;
    private final TaxRepository taxRepository;
    private final NationalInsuranceRepository nationalInsuranceRepository;
    private final StudentLoanRepository studentLoanRepository;
    private final RoleRepository roleRepository;



    @Override
    public void run(String... args) throws Exception {
        createTaxInfo();
        createRoles();
    }

    private void createRoles() {


        Role role = new Role();
        role.setName(RoleName.ROLE_ADMINISTRATOR);

        if(!roleRepository.existsByName(RoleName.ROLE_ADMINISTRATOR)) {
            roleRepository.save(role);
        }


    }

    private void createTaxInfo() {
        Tax tax = new Tax();
        tax.setTaxYearStarts(LocalDate.of(2019,4,6));
        tax.setPersonalAllowance(12500);
        tax.setBasicThreshold(37500);
        tax.setBasicRate(20);
        tax.setHigherThreshold(150000);
        tax.setHigherRate(40);
        tax.setAdditionalRate(45);

        NationalInsurance nationalInsurance = new NationalInsurance();
        nationalInsurance.setTaxYearStarts(LocalDate.of(2019,4,6));
        nationalInsurance.setClassTwo(3);
        nationalInsurance.setClassTwoThreshold(6365);
        nationalInsurance.setLowerProfitsLimit(8632);
        nationalInsurance.setLowerProfitsLimitRate(9);
        nationalInsurance.setUpperProfitsLimit(50000);
        nationalInsurance.setUpperProfitsLimitRate(2);

        StudentLoan studentLoan = new StudentLoan();
        studentLoan.setTaxYearStarts(LocalDate.of(2019,4,6));
        studentLoan.setPlanOneAnnualThreshold(18935);
        studentLoan.setPlanOneRate(9);
        studentLoan.setPlanTwoAnnualThreshold(25725);
        studentLoan.setPlanTwoRate(9);


        TaxYear taxYearDTO = new TaxYear();
        taxYearDTO.setTaxYearStarts(LocalDate.of(2019,4,6));
        taxYearDTO.setTax(tax);
        taxYearDTO.setNationalInsurance(nationalInsurance);
        taxYearDTO.setStudentLoan(studentLoan);

        taxRepository.save(tax);
        nationalInsuranceRepository.save(nationalInsurance);
        studentLoanRepository.save(studentLoan);

        taxYearRepository.save(taxYearDTO);

        System.out.println("Count: " + taxRepository.count());
    }
}
