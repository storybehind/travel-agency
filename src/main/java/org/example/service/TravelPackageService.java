package org.example.service;

import jakarta.transaction.Transactional;
import org.example.domain.entity.Activity;
import org.example.domain.entity.Passenger;
import org.example.domain.entity.TravelPackage;
import org.example.domain.repository.ActivityRepository;
import org.example.domain.repository.PassengerRepository;
import org.example.domain.repository.TravelPackageRepository;
import org.example.service.signup.SignUpActivityFactory;
import org.example.utils.ApiException;
import org.example.utils.constant.ErrorCode;
import org.example.utils.model.SignUpRequestModel;
import org.example.utils.model.SignUpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
