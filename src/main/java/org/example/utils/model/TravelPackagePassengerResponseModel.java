package org.example.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TravelPackagePassengerResponseModel {

    /**
     * Name of travelPackage
     */
    private String travelPackageName;

    /**
     * Maximum number of passengers allowed to sign up
     */
    private int passengerCapacity;

    /**
     * Number of passengers currently signed up
     */
    private int passengersEnrolled;

    /**
     * Details of all signed up passenger
     */
    private List<PassengerModel> passengers;

    @Getter
    @Setter
    public static class PassengerModel {
        /**
         * Unique passenger number
         */
        private int passengerNumber;

        /**
         * Name of passenger
         */
        private String name;
    }
}
