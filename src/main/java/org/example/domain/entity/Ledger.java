package org.example.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ledger")
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "travel_package_id")
    private TravelPackage travelPackage;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
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

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setPricePaid(BigDecimal pricePaid) {
        this.pricePaid = pricePaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
