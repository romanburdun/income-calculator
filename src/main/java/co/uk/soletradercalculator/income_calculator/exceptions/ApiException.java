package co.uk.soletradercalculator.income_calculator.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


@AllArgsConstructor
@Getter
public class ApiException {
    private final String error;
    private final ZonedDateTime timestamp;


}
