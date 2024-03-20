package org.trainticketbook.ticketbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trainticketbook.ticketbook.dto.*;
import org.trainticketbook.ticketbook.exception.TicketFullException;
import org.trainticketbook.ticketbook.exception.UserNotFoundException;
import org.trainticketbook.ticketbook.repo.TicketBookRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketBookService {
    @Autowired
    private TicketBookRepository ticketBookRepository;

    public Ticket purchaseTicket(String from, String to, User user) {
        String seatPick = assignSeat();
        if (seatPick.equals("N/A")) {
            throw new TicketFullException("Sorry, all tickets are sold out.");
        }
        String seatSec = String.valueOf(seatPick.charAt(0));
        int seatnum = Integer.parseInt(seatPick.substring(1));
        Ticket ticket = new Ticket(from, to, user, 5,seatnum,seatSec, LocalDateTime.now());
        return ticketBookRepository.save(ticket);
    }

    public Ticket viewReceipt(String email) {
        Ticket ticket = ticketBookRepository.findByUserEmail(email);
        if(ticket == null) {
            throw new UserNotFoundException("Sorry, User not booked a seat");
        }
        return ticket;
    }

    public List<Ticket> viewAllocation(String section) {
        return ticketBookRepository.findBySeatSection(section);
    }

    public void removeUser(String email) {
        ticketBookRepository.deleteByUserEmail(email);
    }

    public void modifySeat(String email, String newSection, int newSeatNumber) {
        Ticket ticket = ticketBookRepository.findByUserEmail(email);
        if (ticket != null) {
            ticket.setSeatSection(newSection);
            ticket.setSeatNumber(newSeatNumber);
            ticketBookRepository.save(ticket);
        }
    }

    private String assignSeat() {
        Integer maxSeatNumberA = ticketBookRepository.findMaxSeatNumberBySection("A");
        Integer maxSeatNumberB = ticketBookRepository.findMaxSeatNumberBySection("B");

        int nextSeatNumber;
        if (maxSeatNumberA == null && maxSeatNumberB == null) {
            nextSeatNumber = 1;
        } else if (maxSeatNumberA == null) {
            nextSeatNumber = maxSeatNumberB + 1;
        } else if (maxSeatNumberB == null) {
            nextSeatNumber = maxSeatNumberA + 1;
        } else {
            nextSeatNumber = Math.max(maxSeatNumberA, maxSeatNumberB) + 1;
        }

        if (nextSeatNumber <= 50) {
            return "A" + nextSeatNumber;
        } else if (nextSeatNumber <= 100) {
            return "B" + (nextSeatNumber - 50);
        } else {
            return "N/A";
        }
    }
}