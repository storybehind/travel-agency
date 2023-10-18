package org.example.utils.model;

import java.util.List;

public class TravelPackageResponseModel {

    private String travelPackageName;

    private List<ItineraryResponseModel> itineraries;

    public String getTravelPackageName() {
        return travelPackageName;
    }

    public void setTravelPackageName(String travelPackageName) {
        this.travelPackageName = travelPackageName;
    }

    public List<ItineraryResponseModel> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<ItineraryResponseModel> itineraries) {
        this.itineraries = itineraries;
    }
}
