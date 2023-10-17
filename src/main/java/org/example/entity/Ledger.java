package org.example.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ledger")
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "travel_package_name")
    private TravelPackage travelPackage;

    @OneToOne
    @JoinColumn(name = "activity_name")
    private Activity activity;

    @OneToOne
    @JoinColumn(name = "passenger_number")
    private Passenger passenger;

    @Column(name = "price_paid")
    private BigDecimal pricePaid;

    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public Activity getActivity() {
        return activity;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public BigDecimal getPricePaid() {
        return pricePaid;
    }
}
