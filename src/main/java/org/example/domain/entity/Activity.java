package org.example.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "activity", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "capacity")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public int getId() {
        return id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return id == activity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}