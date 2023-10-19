package org.example.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Model used to represent Itinerary
 */
@Getter
@Setter
public class ItineraryResponseModel {

    /**
     * Itinerary name
     */
    private String itineraryName;

    /**
     * List of activities
     */
    private List<ActivityResponseModel> activities;

}
