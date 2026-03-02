package org.autoflex.exception.mapper;

import org.autoflex.exception.exceptions.NoMoreElementsAvailableException;
import org.autoflex.exception.exceptions.NoSuchElementException;
import org.autoflex.exception.exceptions.ProductAlreadyExistsException;
import org.autoflex.exception.exceptions.RawMaterialAlreadyExistsException;
import org.autoflex.exception.exceptions.RawMaterialInUseException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class Global4XXExceptionMapper
        implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {

        if (exception instanceof NoSuchElementException
            || exception instanceof NoMoreElementsAvailableException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        if (exception instanceof ProductAlreadyExistsException
            || exception instanceof RawMaterialAlreadyExistsException
            || exception instanceof RawMaterialInUseException) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}