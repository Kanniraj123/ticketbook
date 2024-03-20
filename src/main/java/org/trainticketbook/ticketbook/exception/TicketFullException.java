package org.trainticketbook.ticketbook.exception;

public class TicketFullException extends RuntimeException {
    public TicketFullException(String message) {
        super(message);
    }
}
