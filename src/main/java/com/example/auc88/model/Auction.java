package com.example.auc88.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Auction {
    private static int idCounter = 1000;
    private final int id;
    private String name;
    private String description;
    private double startPrice;
    private double currentBid;
    private String highestBidder;
    private LocalDateTime endTime;
    private String status; // OPEN, RUNNING, FINISHED

    public final StringProperty timeLeft = new SimpleStringProperty("—");

    public Auction(String name, String description, double startPrice, int hoursDuration) {
        this.id = idCounter++;
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.currentBid = startPrice;
        this.highestBidder = "—";
        this.endTime = LocalDateTime.now().plusHours(hoursDuration);
        this.status = "OPEN";
    }

    // getters & setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getCurrentBid() { return currentBid; }
    public void setCurrentBid(double bid, String bidder) {
        this.currentBid = bid;
        this.highestBidder = bidder;
    }
    public String getHighestBidder() { return highestBidder; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void updateTimeLeft() {
        if (status.equals("FINISHED")) {
            timeLeft.set("ENDED");
            return;
        }
        long seconds = java.time.Duration.between(LocalDateTime.now(), endTime).getSeconds();
        if (seconds <= 0) {
            status = "FINISHED";
            timeLeft.set("ENDED");
        } else {
            timeLeft.set(String.format("%02d:%02d:%02d", seconds/3600, (seconds%3600)/60, seconds%60));
        }
    }
}