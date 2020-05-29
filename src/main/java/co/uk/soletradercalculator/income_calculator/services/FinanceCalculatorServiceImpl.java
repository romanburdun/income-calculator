package co.uk.soletradercalculator.income_calculator.services;

import co.uk.soletradercalculator.income_calculator.api.v1.model.*;
import co.uk.soletradercalculator.income_calculator.requests.CalculationsRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FinanceCalculatorServiceImpl implements FinanceCalculatorService {
    private final TaxYearService taxYearService;
    @Override
    public ReportDTO generateReport(CalculationsRequest request, int taxYear) {



            double income = request.getIncome() - request.getExpenses();



            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setTax(getTaxDeductions(request, taxYear));
            reportDTO.setNicTwo(getNationalInsuranceTwoDeductions(income, request.getIsEmployed(), taxYear));
            reportDTO.setNicFour(getNationalInsuranceFourDeductions(income, taxYear));

            reportDTO.setStudentLoan(getStudentLoanDeductions(request, taxYear, request.getStudentLoan()));
            reportDTO.setSalary(getSalaryBreakDown(income));
            reportDTO.setTakingHome(getTakingHome(reportDTO.getSalary(), reportDTO.getTax(), reportDTO.getNicTwo(), reportDTO.getNicFour(), reportDTO.getStudentLoan()));
            return reportDTO;
        }


    private BreakDownDTO getSalaryBreakDown(double salary) {
        BreakDownDTO salaryDTO = new BreakDownDTO();
        salaryDTO.setAnnual(salary);
        salaryDTO.setMonthly(round(salary/12));
        salaryDTO.setWeekly(round(salary / 52));
        salaryDTO.setDaily(round(salary/52/5));

        return salaryDTO;
    }

    private BreakDownDTO getNationalInsuranceTwoDeductions(double income, boolean isEmployed, int taxYear) {

        BreakDownDTO nicTwoDeductionsDTO = new BreakDownDTO();
        NationalInsuranceDTO nationalInsurance = taxYearService.getRequestedTaxYear(taxYear).getNationalInsurance();


            if(income > nationalInsurance.getClassTwoThreshold()) {
                double nicTwoRate = taxYearService.getRequestedTaxYear(taxYear).getNationalInsurance().getClassTwo()*52;
                nicTwoDeductionsDTO.setAnnual(round(nicTwoRate));
                nicTwoDeductionsDTO.setMonthly(round(nicTwoRate/12));
                nicTwoDeductionsDTO.setWeekly(round(nicTwoRate/52));
                nicTwoDeductionsDTO.setDaily(round(nicTwoDeductionsDTO.getWeekly()/5));
            }


        return nicTwoDeductionsDTO;
    }

    private BreakDownDTO getNationalInsuranceFourDeductions(double income, int taxYear) {
       NationalInsuranceDTO nationalInsurance = taxYearService.getRequestedTaxYear(taxYear).getNationalInsurance();




       BreakDownDTO niDeductionsDTO = new BreakDownDTO();



       double result=0.0;

       if(income > nationalInsurance.getLowerProfitsLimit() && income <= nationalInsurance.getUpperProfitsLimit()) {
           result = income - nationalInsurance.getLowerProfitsLimit();
           double rate =  nationalInsurance.getLowerProfitsLimitRate()/100;
           result= result * rate;


       }

       if(income > nationalInsurance.getUpperProfitsLimit()) {

           double lplDeduction = (nationalInsurance.getUpperProfitsLimit() - nationalInsurance.getLowerProfitsLimit()) * nationalInsurance.getLowerProfitsLimitRate()/100;
           result = lplDeduction + (income - nationalInsurance.getUpperProfitsLimit()) * nationalInsurance.getUpperProfitsLimitRate()/100;
       }

       niDeductionsDTO.setAnnual(round(result));
       niDeductionsDTO.setMonthly(round(result/12));
       niDeductionsDTO.setWeekly(round(result/52));
       niDeductionsDTO.setDaily(round(result/52/5));

       return niDeductionsDTO;

    }


    private BreakDownDTO getTaxDeductions(CalculationsRequest request, int taxYear) {

        TaxYearDTO taxYearDto = taxYearService.getRequestedTaxYear(taxYear);
        TaxDTO tax = taxYearDto.getTax();

        double personalAllowance = tax.getPersonalAllowance();

        if(request.getIsEmployed()) {
            personalAllowance -= request.getEmployedIncome();
        }


        double income = request.getIncome() - request.getExpenses();

        if(income > 100000) {
               double difference = income - 100000;
               personalAllowance = personalAllowance - (difference / 2);

        }

        double result = 0.0;

        if(income > personalAllowance && personalAllowance >= 1 ) {

            if( income < tax.getBasicThreshold()) {
                double taxableIncome = income - personalAllowance;
                result += taxableIncome * tax.getBasicRate()/100;
            } else {
                double taxed = tax.getBasicThreshold() * (double)tax.getBasicRate()/100;
                result += taxed;
            }

            if(income - personalAllowance > tax.getBasicThreshold()) {
                double higherTax = income - personalAllowance - tax.getBasicThreshold();
                double higherRate = tax.getHigherRate()/100;
                result += higherTax * higherRate;
            }


        } else {
            if(income >= tax.getHigherThreshold() || income > 100000) {

                double basic = tax.getBasicThreshold() * (tax.getBasicRate()/100);
                double higher = (tax.getHigherThreshold() - tax.getBasicThreshold()) *  tax.getHigherRate()/100;
                double additional = (income - tax.getHigherThreshold()) * tax.getAdditionalRate()/100;

                result = basic + higher + additional;
            }
        }


        // If user is employed and there is no personal allowance then calculate without personal allowance
        if(personalAllowance <= 0 && request.getIsEmployed()) {
            personalAllowance = 0;

            result = 0.0;

            if(income <= tax.getHigherThreshold()) {

                if (income < tax.getBasicThreshold()) {
                    double taxableIncome = income - personalAllowance;
                    result += taxableIncome * tax.getBasicRate() / 100;
                } else {
                    double taxed = tax.getBasicThreshold() * (double) tax.getBasicRate() / 100;
                    result += taxed;
                }

                if (income - personalAllowance > tax.getBasicThreshold()) {
                    double higherTax = income - personalAllowance - tax.getBasicThreshold();
                    double higherRate = tax.getHigherRate() / 100;
                    result += higherTax * higherRate;
                }
            } else {
                double basic = tax.getBasicThreshold() * (tax.getBasicRate()/100);
                double higher = (tax.getHigherThreshold() - tax.getBasicThreshold()) *  tax.getHigherRate()/100;
                double additional = (income - tax.getHigherThreshold()) * tax.getAdditionalRate()/100;

                result = basic + higher + additional;
            }
        }


        BreakDownDTO taxDeductionsDTO = new BreakDownDTO();
        taxDeductionsDTO.setAnnual(round(result));
        taxDeductionsDTO.setMonthly(round(result/12));
        taxDeductionsDTO.setWeekly(round(result/52));
        taxDeductionsDTO.setDaily(round(result/52/5));
        return taxDeductionsDTO;
    }

    private BreakDownDTO getTakingHome(BreakDownDTO salary, BreakDownDTO taxDeductions, BreakDownDTO nicTwo,
                                       BreakDownDTO nicFour, BreakDownDTO studentLoan) {

        BreakDownDTO incomeDTO = new BreakDownDTO();


        double sum = 0.0;
        sum += taxDeductions.getAnnual();
        sum += nicTwo.getAnnual();
        sum += nicFour.getAnnual();
        sum += studentLoan.getAnnual();
        double takingHome = salary.getAnnual() - sum;

        incomeDTO.setAnnual(round(takingHome));
        incomeDTO.setMonthly(round(takingHome/12));
        incomeDTO.setWeekly(round(takingHome/52));
        incomeDTO.setDaily(round(takingHome/52/5));

        return incomeDTO;
    }



    private BreakDownDTO getStudentLoanDeductions(CalculationsRequest request, int taxYear, int loanPlan) {

        double income = request.getIncome()-request.getExpenses();

        if(request.getIsEmployed()) {
            income+= request.getEmployedIncome();
        }


        BreakDownDTO studentLoanReportDTO = new BreakDownDTO();
        double planOneThreshold = taxYearService.getRequestedTaxYear(taxYear).getStudentLoan().getPlanOneAnnualThreshold();
        double planOneRate = taxYearService.getRequestedTaxYear(taxYear).getStudentLoan().getPlanOneRate();

        double planTwoThreshold = taxYearService.getRequestedTaxYear(taxYear).getStudentLoan().getPlanTwoAnnualThreshold();
        double planTwoRate = taxYearService.getRequestedTaxYear(taxYear).getStudentLoan().getPlanTwoRate();

        double studentLoan=0.0;

        switch (loanPlan) {
            case 1:
                if(income > planOneThreshold) {
                    double deductable = income - planOneThreshold;
                    studentLoan = round(deductable * planOneRate / 100);

                }
                break;
            case 2:
                if(income > planTwoThreshold) {
                    double deductable =  income - planTwoThreshold;
                    studentLoan = round(deductable * planOneRate / 100);

                }
                break;
            case 3:
                if(income > planTwoThreshold) {
                   studentLoan +=round((income - planTwoThreshold) * (planTwoRate/100));
                    if(income > planOneThreshold) {
                         studentLoan+=round((income - planOneThreshold) * (planOneRate/100));
                    }
                } else {
                    if(income > planOneThreshold) {
                       studentLoan = round((income - planOneThreshold) * (planOneRate/100));

                    }
                }
                break;
            default: return studentLoanReportDTO;
        }

        studentLoanReportDTO.setAnnual(studentLoan);
        studentLoanReportDTO.setMonthly(round(studentLoan/12));
        studentLoanReportDTO.setWeekly(round(studentLoan/52));
        studentLoanReportDTO.setDaily(round(studentLoanReportDTO.getWeekly()/5));
        return studentLoanReportDTO;
    }

    //Helper method
    double round (double value) {
        return Math.round(value * 100.0)/ 100.0;
    }
}
