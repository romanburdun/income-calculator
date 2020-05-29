package co.uk.soletradercalculator.income_calculator.services.authentication;

import co.uk.soletradercalculator.income_calculator.security.requests.AuthRequest;
import co.uk.soletradercalculator.income_calculator.security.responses.AuthToken;

public interface AuthenticationService {
    AuthToken register(AuthRequest request);
    AuthToken login(AuthRequest request);
}
