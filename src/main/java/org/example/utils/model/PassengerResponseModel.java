package org.example.utils.model;

import java.math.BigDecimal;
import java.util.List;

public class PassengerResponseModel {
    private String name;
    private int passengerNumber;
    private BigDecimal balance;
    private List<ActivityModel> activities;

    public static class ActivityModel {
        private String activityName;
        private String travelPackageName;
        private BigDecimal pricePaid;

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getTravelPackageName() {
            return travelPackageName;
        }

        public void setTravelPackageName(String travelPackageName) {
            this.travelPackageName = travelPackageName;
        }

        public BigDecimal getPricePaid() {
            return pricePaid;
        }

        public void setPricePaid(BigDecimal pricePaid) {
            this.pricePaid = pricePaid;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<ActivityModel> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityModel> activities) {
        this.activities = activities;
    }
}
