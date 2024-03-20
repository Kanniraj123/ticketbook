package org.trainticketbook.ticketbook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.trainticketbook.ticketbook.dto.*;

import java.util.List;

@Repository
public interface TicketBookRepository extends JpaRepository<Ticket, Long> {
    Ticket findByUserEmail(String email);

    List<Ticket> findBySeatSection(String section);

    void deleteByUserEmail(String email);

    @Query("SELECT MAX(t.seatNumber) FROM Ticket t WHERE t.seatSection = :section")
    Integer findMaxSeatNumberBySection(@Param("section") String section);
}
