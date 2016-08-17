package com.alevohin.demo;

import com.alevohin.demo.calculator.Calculator;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CalculatorResource {

    private final Calculator summator;
    private final Calculator multiplicator;
    private final Calculator divider;

    public CalculatorResource(final Calculator summator,
        final Calculator multiplicator, final Calculator divider) {
        this.summator = summator;
        this.multiplicator = multiplicator;
        this.divider = divider;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add/{a}/{b}")
    public CalculatorResult add(@PathParam("a") BigDecimal a,
        @PathParam("b") BigDecimal b) {
        List<BigDecimal> operands = Arrays.asList(a, b);
        Collections.sort(operands);
        return CalculatorResult.of(summator.calculate(operands));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add/{a}/{b}/{c}")
    public CalculatorResult add(@PathParam("a") BigDecimal a,
        @PathParam("b") BigDecimal b, @PathParam("c") BigDecimal c) {
        List<BigDecimal> operands = Arrays.asList(a, b, c);
        Collections.sort(operands);
        return CalculatorResult.of(summator.calculate(operands));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("subtract/{a}/{b}")
    public CalculatorResult subtract(@PathParam("a") BigDecimal a,
        @PathParam("b") BigDecimal b) {
        return add(a, b.negate());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("subtract/{a}/{b}/{c}")
    public CalculatorResult subtract(@PathParam("a") BigDecimal a,
        @PathParam("b") BigDecimal b, @PathParam("c") BigDecimal c) {
        return add(a, b.negate(), c.negate());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("multiply/{a}/{b}")
    public CalculatorResult multiply(@PathParam("a") BigDecimal a,
        @PathParam("b") BigDecimal b) {
        List<BigDecimal> operands = Arrays.asList(a, b);
        Collections.sort(operands);
        return CalculatorResult.of(multiplicator.calculate(operands));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("multiply/{a}/{b}/{c}")
    public CalculatorResult multiply(@PathParam("a") BigDecimal a,
        @PathParam("b") BigDecimal b, @PathParam("c") BigDecimal c) {
        List<BigDecimal> operands = Arrays.asList(a, b, c);
        Collections.sort(operands);
        return CalculatorResult.of(multiplicator.calculate(operands));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("divide/{a}/{b}")
    public CalculatorResult divide(@PathParam("a") BigDecimal a,
        @PathParam("b") BigDecimal b) {
        return CalculatorResult.of(divider.calculate(Arrays.asList(a, b)));
    }
}
