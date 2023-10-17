package org.example.entity;

import org.example.constant.PassengerMembership;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_number")
    private int passengerNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "membership")
    @Enumerated(EnumType.STRING)
    private PassengerMembership membership;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToMany(mappedBy = "passengers")
    private Set<TravelPackage> travelPackageSet = new HashSet<>();

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public String getName() {
        return name;
    }

    public PassengerMembership getMembership() {
        return membership;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Set<TravelPackage> getTravelPackageSet() {
        return travelPackageSet;
    }

    public void addTravelPackage(TravelPackage travelPackage) {
        travelPackageSet.add(travelPackage);
        if (!travelPackage.getPassengers().contains(this)) {
            travelPackage.addPassenger(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return passengerNumber == passenger.passengerNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerNumber);
    }
}
