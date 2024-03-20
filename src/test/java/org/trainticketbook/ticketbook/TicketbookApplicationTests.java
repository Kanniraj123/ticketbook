package org.trainticketbook.ticketbook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.trainticketbook.ticketbook.dto.Ticket;
import org.trainticketbook.ticketbook.dto.User;
import org.trainticketbook.ticketbook.repo.TicketBookRepository;
import org.trainticketbook.ticketbook.service.TicketBookService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TicketbookApplicationTests {

    @Mock
    private TicketBookRepository ticketBookRepository;

    @InjectMocks
    private TicketBookService ticketBookService;

    @Test
    public void testPurchaseTicket() {

        String from = "London";
        String to = "France";
        User user = new User("John", "Doe", "john.doe@example.com");
        Ticket ticket = new Ticket(from, to, user, 5, 12, "A", LocalDateTime.now());
        when(ticketBookRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket purchasedTicket = ticketBookService.purchaseTicket(from, to, user);

        assertNotNull(purchasedTicket);
        assertEquals(from, purchasedTicket.getFrom());
        assertEquals(to, purchasedTicket.getTo());
        assertEquals(user, purchasedTicket.getUser());
        assertEquals(5, purchasedTicket.getPricePaid());
        assertEquals("A", purchasedTicket.getSeatSection());
        assertNotNull(purchasedTicket.getPurchaseTime());
        verify(ticketBookRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testViewReceipt() {
        String email = "john.doe@example.com";
        User user = new User("John", "Doe", email);
        Ticket ticket = new Ticket("London", "France", user, 5, 11, "A", LocalDateTime.now());
        when(ticketBookRepository.findByUserEmail(email)).thenReturn(ticket);

        Ticket receipt = ticketBookService.viewReceipt(email);

        assertNotNull(receipt);
        assertEquals(email, receipt.getUser().getEmail());
        verify(ticketBookRepository, times(1)).findByUserEmail(email);
    }

    @Test
    public void testViewAllocation() {
        String section = "A";
        Ticket ticket1 = new Ticket("London", "France", new User("John", "Doe", "john.doe@example.com"), 6, 11, "A", LocalDateTime.now());

        Ticket ticket2 = new Ticket("Paris", "Berlin", new User("Alice", "Smith", "alice.smith@example.com"), 5, 23, "B", LocalDateTime.now());
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketBookRepository.findBySeatSection(section)).thenReturn(tickets);

        List<Ticket> allocation = ticketBookService.viewAllocation(section);

        assertNotNull(allocation);
        assertEquals(2, allocation.size());
        verify(ticketBookRepository, times(1)).findBySeatSection(section);
    }

    @Test
    public void testRemoveUser() {
        String email = "john.doe@example.com";

        ticketBookService.removeUser(email);

        verify(ticketBookRepository, times(1)).deleteByUserEmail(email);
    }

    @Test
    public void testModifySeat() {
        String email = "john.doe@example.com";
        Ticket ticket = new Ticket("London", "France", new User("John", "Doe", email), 5, 15, "A", LocalDateTime.now());
        when(ticketBookRepository.findByUserEmail(email)).thenReturn(ticket);

        ticketBookService.modifySeat(email, "B", 10);

        assertEquals("B", ticket.getSeatSection());
        assertEquals(10, ticket.getSeatNumber());
        verify(ticketBookRepository, times(1)).findByUserEmail(email);
        verify(ticketBookRepository, times(1)).save(ticket);
    }
}
