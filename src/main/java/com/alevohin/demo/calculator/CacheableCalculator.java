package com.alevohin.demo.calculator;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheableCalculator implements Calculator{

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheableCalculator.class);

    private final Calculator calculator;
    private Map<Key, BigDecimal> cache = new ConcurrentHashMap<>();

    public CacheableCalculator(final Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public BigDecimal calculate(List<BigDecimal> operands) {
        Key key = new Key(operands);
        BigDecimal value = cache.get(key);
        if (value == null) {
            value = calculator.calculate(operands);
            cache.put(key, value);
            LOGGER.info("Calculated {} for {}", value, operands);
        } else {
            LOGGER.info("Found cached {} for {}", value, operands);
        }
        return value;
    }

    private class Key {
        private Triple<BigDecimal, BigDecimal, BigDecimal> triple;

        public Key(List<BigDecimal> operands) {
            Iterator<BigDecimal> iterator = operands.iterator();
            BigDecimal first = null;
            BigDecimal second = null;
            BigDecimal third = null;

            if (iterator.hasNext()) {
                first = iterator.next();
            }
            if (iterator.hasNext()) {
                second = iterator.next();
            }
            if (iterator.hasNext()) {
                third = iterator.next();
            }
            triple = ImmutableTriple.of(first, second, third);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final Key key = (Key) o;

            if (triple != null ? !triple.equals(key.triple) : key.triple != null)
                return false;

            return true;
        }
        @Override
        public int hashCode() {
            return triple != null ? triple.hashCode() : 0;
        }
    }
}
