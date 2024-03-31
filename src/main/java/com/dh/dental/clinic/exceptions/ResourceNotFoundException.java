package com.dh.dental.clinic.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entityClassName){
        super("Error: " + entityClassName + " not found {}");
    }

}
