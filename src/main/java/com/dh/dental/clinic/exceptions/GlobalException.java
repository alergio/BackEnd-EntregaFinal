package com.dh.dental.clinic.exceptions;

import com.dh.dental.clinic.dto.DTOResponse;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    Logger logger = Logger.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        DTOResponse<Object> response = new DTOResponse<>();

        response.setMessage("An unexpected error occurred on the server");
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        logger.error("ERROR 500: An unexpected error (" + ex.getClass().getSimpleName() + ") occurred on the server. Trace:", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex) {
        DTOResponse<Object> response = new DTOResponse<>();

        response.setMessage("An error has occurred on the server, there are conflicts with the integrity of the data you sent.");
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());

        logger.error("ERROR 400: BAD REQUEST (" + ex.getClass().getSimpleName() + ") occurred on the server. Trace:", ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex) {
        DTOResponse<Object> response = new DTOResponse<>();

        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());

        logger.error("ERROR 404: Resource Not Found Exception.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


}
