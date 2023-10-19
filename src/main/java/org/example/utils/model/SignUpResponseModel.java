package org.example.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Model used to represent response after successful sign up
 */
@Getter
@Setter
public class SignUpResponseModel {

    /**
     * Details of activities that passenger has signed up
     */
    private List<ActivityResponseModel> activityResponseModels;

    /**
     * Represents passenger's remaining balance
     */
    private BigDecimal balance;

    @Getter
    @Setter
    public static class ActivityResponseModel {

        /**
         * Identifier of activity
         */
        private int activityId;

        /**
         * Name of activity
         */
        private String activityName;

        /**
         * Cost charged to passenger for the activity
         */
        private BigDecimal costPaid;

    }

}
