package ru.shaldin.eventsourcing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class Subscription {
    private int id;
    private Date creationDate;
    private Date expiryDate;

    public Subscription(int id, Date creationDate, Date expiryDate1) {
        this.id = id;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate1;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public int getId() {
        return id;
    }
}
