package com.alevohin.demo;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CalculatorExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception exception) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(CalculatorResult.error(exception.getMessage()))
            .build();
    }
}
