package co.uk.soletradercalculator.income_calculator.security.requests;

import lombok.Getter;

@Getter
public class AuthRequest {

    private String email;
    private String password;
}
