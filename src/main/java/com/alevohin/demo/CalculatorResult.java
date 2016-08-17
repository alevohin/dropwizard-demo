package com.alevohin.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CalculatorResult {

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("error")
    private String error;

    private CalculatorResult() {
    }

    private CalculatorResult(final BigDecimal value, final String error) {
        this.value = value;
        this.error = error;
    }

    public BigDecimal getValue() {
        return value;
    }
    public String getError() {
        return error;
    }
    public static CalculatorResult of(BigDecimal value) {
        return new CalculatorResult(value.stripTrailingZeros(), null);
    }

    public static CalculatorResult error(String message) {
        return new CalculatorResult(null, message);
    }
}
