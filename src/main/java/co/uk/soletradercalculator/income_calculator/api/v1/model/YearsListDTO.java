package co.uk.soletradercalculator.income_calculator.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class YearsListDTO {
    List<Integer> yearsList;
}
