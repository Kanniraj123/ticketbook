package org.trainticketbook.ticketbook.dto;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source")
    private String from;

    @Column(name = "destination")
    private String to;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    private int pricePaid;
    private String seatSection;
    private int seatNumber;
    private LocalDateTime purchaseTime;
    public Ticket(String from, String to, User user, int pricePaid, int seatNumber, String seatSection,
                  LocalDateTime purchaseTime) {
        this.from = from;
        this.to = to;
        this.user = user;
        this.pricePaid = pricePaid;
        this.seatSection = seatSection;
        this.seatNumber = seatNumber;
        this.purchaseTime = purchaseTime;
    }

    public Ticket() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getPricePaid() {
        return pricePaid;
    }
    public void setPricePaid(int pricePaid) {
        this.pricePaid = pricePaid;
    }
    public String getSeatSection() {
        return seatSection;
    }
    public void setSeatSection(String seatSection) {
        this.seatSection = seatSection;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }
    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }




}
