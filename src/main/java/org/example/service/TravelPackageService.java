package org.example.service;

import org.example.domain.entity.*;
import org.example.domain.repository.ActivityRepository;
import org.example.domain.repository.LedgerRepository;
import org.example.domain.repository.PassengerRepository;
import org.example.domain.repository.TravelPackageRepository;
import org.example.service.signup.CalculateCostActivityFactory;
import org.example.utils.ApiException;
import org.example.utils.constant.ErrorCode;
import org.example.utils.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private LedgerRepository ledgerRepository;

    @Autowired
    private CalculateCostActivityFactory calculateCostActivityFactory;

    /**
     * <p>Signs Up a given passenger to the list of activities present in travel package</p>
     *
     * <p>On Successful SignUp, return {@link SignUpResponseModel}. The SignUp is successful only if all activities can be signed up</p>
     *
     * <p>Throws {@link ApiException} with following {@link ErrorCode}</p>
     *
     * <p>{@link ErrorCode#INVALID_TRAVEL_PACKAGE_IDENTIFIER} if travelPackageId of {@link SignUpRequestModel} is invalid</p>
     * <p>{@link ErrorCode#INVALID_PASSENGER_IDENTIFIER} if passengerNumber of {@link SignUpRequestModel} is invalid</p>
     * <p>{@link ErrorCode#INVALID_ACTIVITY_IDENTIFIER} if any of the activityId of {@link SignUpRequestModel} is invalid</p>
     * <p>{@link ErrorCode#ACTIVITY_NOT_PRESENT_IN_TRAVEL_PACKAGE} if any of the activityId doesn't belong to travelPackageId of {@link SignUpRequestModel}</p>
     *
     * <p>{@link ErrorCode#ACTIVITY_CAPACITY_REACHED} if any of the activities doesn't have enough capacity</p>
     * <p>{@link ErrorCode#PASSENGER_LIMIT_REACHED} if travelPackage has reached its passenger limit</p>
     * <p>{@link ErrorCode#INSUFFICIENT_FUND} if passenger doesn't have enough balance to sign up all the activities</p>
     */
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

        List<SignUpResponseModel.ActivityResponseModel> activityResponseModels = calculateCostActivityFactory.getService(passenger.getMembership()).calculateCost(activities);
        BigDecimal totalCostRequired = BigDecimal.ZERO;
        for (SignUpResponseModel.ActivityResponseModel activityResponseModel : activityResponseModels) {
            totalCostRequired = totalCostRequired.add(activityResponseModel.getCostPaid());
        }
        if (totalCostRequired.compareTo(passenger.getBalance()) > 0) {
            throw new ApiException(ErrorCode.INSUFFICIENT_FUND, "TotalCostRequired = " + totalCostRequired + " , balance = " + passenger.getBalance());
        }

        travelPackage.addPassenger(passenger);
        passenger.setBalance(passenger.getBalance().subtract(totalCostRequired));
        List<Ledger> ledgers = new ArrayList<>();
        for (Activity activity : activities) {
            Ledger ledger = new Ledger();
            ledger.setTravelPackage(travelPackage);
            ledger.setActivity(activity);
            ledger.setPassenger(passenger);
            ledger.setPricePaid(activity.getCost());
            ledgers.add(ledger);
        }
        ledgerRepository.saveAll(ledgers);

        SignUpResponseModel signUpResponseModel = new SignUpResponseModel();
        signUpResponseModel.setActivityResponseModels(activityResponseModels);
        signUpResponseModel.setBalance(passenger.getBalance());
        return signUpResponseModel;
    }

    /**
     * Returns {@link TravelPackageResponseModel} on successful retrieval
     * Throws {@link ApiException} with {@link ErrorCode#INVALID_TRAVEL_PACKAGE_IDENTIFIER} if travelPackageId is invalid
     */
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
            itineraryResponseModels.add(itineraryResponseModel);
        }
        travelPackageResponseModel.setItineraries(itineraryResponseModels);
        return travelPackageResponseModel;
    }

    /**
     * Returns {@link TravelPackagePassengerResponseModel} on successful retrieval
     * Throws {@link ApiException} with {@link ErrorCode#INVALID_TRAVEL_PACKAGE_IDENTIFIER} if travelPackageId is invalid
     */
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
