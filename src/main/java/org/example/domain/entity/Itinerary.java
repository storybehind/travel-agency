package org.example.domain.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "itinerary", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "itinerary")
    private Set<Activity> activitySet = new HashSet<>();

    @ManyToMany(mappedBy = "itineraries")
    private Set<TravelPackage> travelPackages = new HashSet<>();

    public String getName() {
        return name;
    }

    public Set<Activity> getActivitySet() {
        return activitySet;
    }

    public Set<TravelPackage> getTravelPackages() {
        return travelPackages;
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

    public void setActivitySet(Set<Activity> activitySet) {
        this.activitySet = activitySet;
    }

    public void setTravelPackages(Set<TravelPackage> travelPackages) {
        this.travelPackages = travelPackages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return id == itinerary.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}