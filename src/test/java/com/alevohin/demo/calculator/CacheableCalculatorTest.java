package com.alevohin.demo.calculator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheableCalculatorTest {

    @InjectMocks
    private CacheableCalculator cacheableCalculator;

    @Mock
    private Calculator calculator;

    @Test
    public void cachingTwoOperands() {
        List<BigDecimal> operands = Arrays.asList(
            BigDecimal.valueOf(nextLong(0, Long.MAX_VALUE)),
            BigDecimal.valueOf(nextLong(0, Long.MAX_VALUE))
        );
        when(calculator.calculate(any())).thenReturn(
            BigDecimal.valueOf(nextLong(0, Long.MAX_VALUE))
        );
        for (int i=0; i<nextInt(10, 100); i++) {
            cacheableCalculator.calculate(operands);
        }
        verify(calculator, times(1)).calculate(any());
    }

    @Test
    public void cachingThreeOperands() {
        List<BigDecimal> operands = Arrays.asList(
            BigDecimal.valueOf(nextLong(0, Long.MAX_VALUE)),
            BigDecimal.valueOf(nextLong(0, Long.MAX_VALUE)),
            BigDecimal.valueOf(nextLong(0, Long.MAX_VALUE))
        );
        when(calculator.calculate(any())).thenReturn(
            BigDecimal.valueOf(nextLong(0, Long.MAX_VALUE))
        );

        for (int i=0; i<nextInt(10, 100); i++) {
            cacheableCalculator.calculate(operands);
        }
        verify(calculator, times(1)).calculate(any());
    }
}
