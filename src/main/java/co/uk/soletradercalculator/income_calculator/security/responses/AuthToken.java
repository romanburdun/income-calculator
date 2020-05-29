package co.uk.soletradercalculator.income_calculator.security.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthToken {
    private String jwt;
}
