package org.example.utils.model;

import java.util.List;

public class SignUpRequestModel {
    private int travelPackageId;
    private int passengerNumber;
    private List<Integer> activityIds;

    public int getTravelPackageId() {
        return travelPackageId;
    }

    public void setTravelPackageId(int travelPackageId) {
        this.travelPackageId = travelPackageId;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public List<Integer> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<Integer> activityIds) {
        this.activityIds = activityIds;
    }
}
