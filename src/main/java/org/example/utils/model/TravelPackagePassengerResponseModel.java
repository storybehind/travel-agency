package org.example.utils.model;

import java.util.List;

public class TravelPackagePassengerResponseModel {
    private String travelPackageName;
    private int passengerCapacity;
    private int passengersEnrolled;
    private List<PassengerModel> passengers;

    public static class PassengerModel {
        private int passengerNumber;
        private String name;

        public int getPassengerNumber() {
            return passengerNumber;
        }

        public void setPassengerNumber(int passengerNumber) {
            this.passengerNumber = passengerNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getTravelPackageName() {
        return travelPackageName;
    }

    public void setTravelPackageName(String travelPackageName) {
        this.travelPackageName = travelPackageName;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengersEnrolled() {
        return passengersEnrolled;
    }

    public void setPassengersEnrolled(int passengersEnrolled) {
        this.passengersEnrolled = passengersEnrolled;
    }

    public List<PassengerModel> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerModel> passengers) {
        this.passengers = passengers;
    }
}
