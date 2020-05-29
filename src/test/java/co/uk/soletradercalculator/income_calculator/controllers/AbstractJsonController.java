package co.uk.soletradercalculator.income_calculator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractJsonController {
    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
