package com.pdi.deloitte.EventManagement.Exception;

public class VenueAlreadyBookedException extends RuntimeException {
    public VenueAlreadyBookedException(String message) {
        super(message);
    }
}

