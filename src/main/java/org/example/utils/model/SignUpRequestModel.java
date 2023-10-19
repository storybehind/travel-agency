package org.example.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Model used to sign up a passenger to one or more activities present in travel package
 */
@Getter
@Setter
public class SignUpRequestModel {
    /**
     * Identifier of travel package
     */
    private int travelPackageId;

    /**
     * Identifier of passenger
     */
    private int passengerNumber;

    /**
     * List of activity ID's
     */
    private List<Integer> activityIds;

}
