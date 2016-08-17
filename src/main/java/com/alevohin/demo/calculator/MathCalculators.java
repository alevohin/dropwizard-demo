package com.alevohin.demo.calculator;

import java.math.BigDecimal;
import java.util.Iterator;

public class MathCalculators {

    public static Calculator summator() {
        return operands -> {
            BigDecimal result = BigDecimal.ZERO;
            for (BigDecimal operand : operands) {
                result = result.add(operand);
            }
            return result;
        };
    }

    public static Calculator multiplicator() {
        return operands -> {
            BigDecimal result = BigDecimal.ONE;
            for (BigDecimal operand : operands) {
                result = result.multiply(operand, Calculator.MATH_CTX);
            }
            return result;
        };
    }

    public static Calculator divider() {
        return operands -> {
            final Iterator<BigDecimal> iterator = operands.iterator();
            BigDecimal operand1 = iterator.next();
            BigDecimal operand2 = iterator.next();
            return operand1.divide(operand2, Calculator.MATH_CTX);
        };
    }
}
