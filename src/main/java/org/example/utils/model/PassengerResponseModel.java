package org.example.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PassengerResponseModel {
    /**
     * Name of passenger
     */
    private String name;

    /**
     * Unique Passenger Number
     */
    private int passengerNumber;

    /**
     * Passenger remaining balance
     */
    private BigDecimal balance;

    /**
     * activities signed up by passenger
     */
    private List<ActivityModel> activities;

    @Getter
    @Setter
    public static class ActivityModel {
        /**
         * Name of activity
         */
        private String activityName;

        /**
         * Name of travel package
         */
        private String travelPackageName;

        /**
         * Price paid for the activity
         */
        private BigDecimal pricePaid;
    }
}
