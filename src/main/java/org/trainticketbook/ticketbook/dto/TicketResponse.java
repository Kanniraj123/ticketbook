package org.trainticketbook.ticketbook.dto;

import java.time.LocalDateTime;

public class TicketResponse {

    private String from;
    private String to;
    private User user;
    private int pricePaid;
    private String seatSection;
    private int seatNumber;
    private LocalDateTime purchaseTime;
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
