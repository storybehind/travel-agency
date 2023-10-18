package org.example.domain.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "travel_package", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    @ManyToMany
    @JoinTable(
            name = "travel_package_itinerary_map",
            joinColumns = @JoinColumn(name = "travel_package_id"),
            inverseJoinColumns = @JoinColumn(name = "itinerary_id")
    )
    private Set<Itinerary> itineraries = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "travel_package_passenger_map",
            joinColumns = @JoinColumn(name = "travel_package_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_number")
    )
    private Set<Passenger> passengers = new HashSet<>();

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public Set<Itinerary> getItineraries() {
        return itineraries;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() == passengerCapacity) {
            throw new RuntimeException("Passenger Limit Reached");
        }
        passengers.add(passenger);
        if (!passenger.getTravelPackageSet().contains(this)) {
            passenger.addTravelPackage(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public void setItineraries(Set<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelPackage that = (TravelPackage) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}