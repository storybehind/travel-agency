package org.example.service;

import org.example.domain.entity.Activity;
import org.example.domain.entity.Itinerary;
import org.example.domain.entity.Passenger;
import org.example.domain.entity.TravelPackage;
import org.example.domain.repository.ActivityRepository;
import org.example.domain.repository.PassengerRepository;
import org.example.domain.repository.TravelPackageRepository;
import org.example.service.signup.SignUpActivityFactory;
import org.example.utils.ApiException;
import org.example.utils.constant.ErrorCode;
import org.example.utils.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TravelPackageService {

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private SignUpActivityFactory signUpActivityFactory;

    @Transactional
    public SignUpResponseModel signUp(SignUpRequestModel signUpRequestModel) throws ApiException {
        Optional<TravelPackage> travelPackageOptional = travelPackageRepository.findById(signUpRequestModel.getTravelPackageId());
        TravelPackage travelPackage =  travelPackageOptional.orElseThrow(() -> new ApiException(ErrorCode.INVALID_TRAVEL_PACKAGE_IDENTIFIER));

        Optional<Passenger> passengerOptional = passengerRepository.findById(signUpRequestModel.getPassengerNumber());
        Passenger passenger = passengerOptional.orElseThrow(() -> new ApiException(ErrorCode.INVALID_PASSENGER_IDENTIFIER));

        List<Activity> activities = new ArrayList<>();
        for (int activityId : signUpRequestModel.getActivityIds()) {
            Optional<Activity> activityOptional = activityRepository.findById(activityId);
            Activity activity = activityOptional.orElseThrow(() -> new ApiException(ErrorCode.INVALID_ACTIVITY_IDENTIFIER));
            if (!travelPackage.getItineraries().contains(activity.getItinerary())) {
                throw new ApiException(ErrorCode.ACTIVITY_NOT_PRESENT_IN_TRAVEL_PACKAGE, "Activity ID: " + activityId + " does not belong to Travel Package ID: " + travelPackage.getId());
            }
            if (activity.getCapacity() == 0) {
                throw new ApiException(ErrorCode.ACTIVITY_CAPACITY_REACHED, "Activity : " + activity.getName() + " has no enough capacity");
            }
            activity.setCapacity(activity.getCapacity() - 1);
            activities.add(activity);
        }

        if (!travelPackage.getPassengers().contains(passenger) && travelPackage.getPassengerCapacity() == travelPackage.getPassengers().size()) {
            throw new ApiException(ErrorCode.PASSENGER_LIMIT_REACHED, "Travel Package ID : " + travelPackage.getId() + "reached its limit");
        }

        return signUpActivityFactory.getService(passenger.getMembership()).signUp(travelPackage, activities, passenger);
    }

    @Transactional(readOnly = true)
    public TravelPackageResponseModel getTravelPackageResponseModel(int travelPackageId) throws ApiException {
        Optional<TravelPackage> travelPackageOptional = travelPackageRepository.findById(travelPackageId);
        TravelPackage travelPackage =  travelPackageOptional.orElseThrow(() -> new ApiException(ErrorCode.INVALID_TRAVEL_PACKAGE_IDENTIFIER));

        TravelPackageResponseModel travelPackageResponseModel = new TravelPackageResponseModel();
        travelPackageResponseModel.setTravelPackageName(travelPackage.getName());
        List<ItineraryResponseModel> itineraryResponseModels = new ArrayList<>();
        for (Itinerary itinerary : travelPackage.getItineraries()) {
            ItineraryResponseModel itineraryResponseModel = new ItineraryResponseModel();
            List<ActivityResponseModel> activityResponseModels = new ArrayList<>();
            for (Activity activity : itinerary.getActivitySet()) {
                ActivityResponseModel activityResponseModel = new ActivityResponseModel();
                activityResponseModel.setName(activity.getName());
                activityResponseModel.setDescription(activity.getDescription());
                activityResponseModel.setCost(activity.getCost());
                activityResponseModel.setCapacity(activity.getCapacity());
                activityResponseModels.add(activityResponseModel);
            }
            itineraryResponseModel.setItineraryName(itinerary.getName());
            itineraryResponseModel.setActivities(activityResponseModels);
        }
        travelPackageResponseModel.setItineraries(itineraryResponseModels);
        return travelPackageResponseModel;
    }

    @Transactional(readOnly = true)
    public TravelPackagePassengerResponseModel getTravelPackagePassengerResponseModel(int travelPackageId) throws ApiException {
        Optional<TravelPackage> travelPackageOptional = travelPackageRepository.findById(travelPackageId);
        TravelPackage travelPackage =  travelPackageOptional.orElseThrow(() -> new ApiException(ErrorCode.INVALID_TRAVEL_PACKAGE_IDENTIFIER));

        TravelPackagePassengerResponseModel travelPackagePassengerResponseModel = new TravelPackagePassengerResponseModel();
        travelPackagePassengerResponseModel.setTravelPackageName(travelPackage.getName());
        travelPackagePassengerResponseModel.setPassengerCapacity(travelPackage.getPassengerCapacity());
        travelPackagePassengerResponseModel.setPassengersEnrolled(travelPackage.getPassengers().size());
        List<TravelPackagePassengerResponseModel.PassengerModel> passengerModels = new ArrayList<>();
        for (Passenger passenger : travelPackage.getPassengers()) {
            TravelPackagePassengerResponseModel.PassengerModel passengerModel = new TravelPackagePassengerResponseModel.PassengerModel();
            passengerModel.setPassengerNumber(passenger.getPassengerNumber());
            passengerModel.setName(passenger.getName());
            passengerModels.add(passengerModel);
        }
        travelPackagePassengerResponseModel.setPassengers(passengerModels);
        return travelPackagePassengerResponseModel;
    }

}
