package org.example.utils.model;

import java.util.List;

public class ItineraryResponseModel {

    private String itineraryName;

    private List<ActivityResponseModel> activities;

    public String getItineraryName() {
        return itineraryName;
    }

    public void setItineraryName(String itineraryName) {
        this.itineraryName = itineraryName;
    }

    public List<ActivityResponseModel> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityResponseModel> activities) {
        this.activities = activities;
    }
}
