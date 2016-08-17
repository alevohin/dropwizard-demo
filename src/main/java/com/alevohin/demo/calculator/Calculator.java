package com.alevohin.demo.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public interface Calculator {

    MathContext MATH_CTX = MathContext.DECIMAL128;

    BigDecimal calculate(List<BigDecimal> operands);

}
