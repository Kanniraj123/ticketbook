package org.trainticketbook.ticketbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.trainticketbook.ticketbook.dto.*;
import org.trainticketbook.ticketbook.exception.TicketFullException;
import org.trainticketbook.ticketbook.exception.UserNotFoundException;
import org.trainticketbook.ticketbook.service.TicketBookService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class TicketBookController {

    @Autowired
    private TicketBookService ticketBookService;

    @PostMapping("/purchase_ticket")
    public ResponseEntity<?> purchaseTicket(@RequestBody PurchaseRequest requests) {
        try {
            Ticket ticket = ticketBookService.purchaseTicket(requests.getFrom(), requests.getTo(), requests.getUser());
            return ResponseEntity.ok(mapToTicketResponse(ticket));
        } catch (TicketFullException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @GetMapping("/view_receipt/{email}")
    public ResponseEntity<?> viewReceipt(@PathVariable String email) {
        try{
        Ticket ticket = ticketBookService.viewReceipt(email);
        return ResponseEntity.ok(mapToTicketResponse(ticket));
        } catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/view_allocation/{section}")
    public ResponseEntity<List<TicketResponse>> viewAllocation(@PathVariable String section) {
        List<Ticket> tickets = ticketBookService.viewAllocation(section);
        return ResponseEntity.ok(mapToTicketResponses(tickets));
    }

    @DeleteMapping("/remove_user")
    public ResponseEntity<String> removeUser(@RequestBody RemoveUserRequest request) {
        ticketBookService.removeUser(request.getEmail());
        return ResponseEntity.ok("User removed successfully");
    }

    @PutMapping("/modify_seat")
    public ResponseEntity<String> modifySeat(@RequestBody ModifySeatRequest request) {
        ticketBookService.modifySeat(request.getEmail(), request.getNewSection(), request.getNewSeatNumber());
        return ResponseEntity.ok("Seat modified successfully");
    }

    private TicketResponse mapToTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setFrom(ticket.getFrom());
        response.setTo(ticket.getTo());
        response.setUser(ticket.getUser());
        response.setPricePaid(ticket.getPricePaid());
        response.setSeatSection(ticket.getSeatSection());
        response.setSeatNumber(ticket.getSeatNumber());
        response.setPurchaseTime(ticket.getPurchaseTime());
        return response;
    }

    private List<TicketResponse> mapToTicketResponses(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::mapToTicketResponse)
                .collect(Collectors.toList());
    }
}