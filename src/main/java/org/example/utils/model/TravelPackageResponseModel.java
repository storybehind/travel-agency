package org.example.utils.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Model used to represent TravelPackage
 */
@Getter
@Setter
public class TravelPackageResponseModel {

    /**
     * Name of travelPackage
     */
    private String travelPackageName;

    /**
     * List of Itineraries
     */
    private List<ItineraryResponseModel> itineraries;
}
