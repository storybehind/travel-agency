package org.example;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.example.controller.ActivityController;
import org.example.controller.TravelPackageController;
import org.example.service.ActivityService;
import org.example.service.TravelPackageService;
import org.example.utils.model.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelAgency.class)
public class TravelAgencyApplicationTests {

    @Autowired
    private TravelPackageController travelPackageController;

    @Autowired
    private ActivityController activityController;

    @Test
    @Sql({"/data.sql"})
    public void testTravelAgency() {
        // verify initial activities set
        List<ActivityResponseModel> activityResponseModels = activityController.activityResponseModelApiResponse().getData();
        assert (activityResponseModels.size() == 7);
        Map<String, ActivityResponseModel> activityResponseModelMap = activityResponseModels.stream().collect(Collectors.toMap(ActivityResponseModel::getName, activityResponseModel -> activityResponseModel));
        assert (activityResponseModelMap.containsKey("activity 1"));
        assert (activityResponseModelMap.get("activity 1").getCapacity() == 3);
        assert (activityResponseModelMap.containsKey("activity 3"));
        assert (activityResponseModelMap.get("activity 3").getCapacity() == 3);

        // verify no passenger is enrolled in travel package id 1
        TravelPackagePassengerResponseModel travelPackagePassengerResponseModel = travelPackageController.travelPackagePassengerResponseModelApiResponse(1).getData();
        assert (travelPackagePassengerResponseModel.getPassengersEnrolled() == 0);

        // signUp passenger 1(STANDARD MEMBER) in activities [1, 3] and travel package id 1
        // verify passenger 1 has balance = 10, costPaid for activity 1 = 10, activity 3 = 30
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        signUpRequestModel.setTravelPackageId(1);
        signUpRequestModel.setPassengerNumber(1);
        signUpRequestModel.setActivityIds(new ArrayList<>() {{
            add(1);
            add(3);
        }});
        SignUpResponseModel signUpResponseModel = travelPackageController.signUp(signUpRequestModel).getData();
        assert (signUpResponseModel != null);
        assert (signUpResponseModel.getBalance().compareTo(BigDecimal.TEN) == 0);
        assert (!signUpResponseModel.getActivityResponseModels().isEmpty());
        Map<Integer, SignUpResponseModel.ActivityResponseModel> signUpActivityResponseModelMap = signUpResponseModel.getActivityResponseModels().stream().collect(Collectors.toMap(SignUpResponseModel.ActivityResponseModel::getActivityId, activityResponseModel -> activityResponseModel));
        assert (signUpActivityResponseModelMap.size() == 2);
        assert (signUpActivityResponseModelMap.containsKey(1));
        assert (signUpActivityResponseModelMap.get(1).getCostPaid().compareTo(BigDecimal.TEN) == 0);
        assert (signUpActivityResponseModelMap.containsKey(3));
        assert (signUpActivityResponseModelMap.get(3).getCostPaid().compareTo(new BigDecimal("30")) == 0);

        // verify activities set has size 7 and activity 1 & 3 has capacity 2
        activityResponseModels = activityController.activityResponseModelApiResponse().getData();
        assert (activityResponseModels.size() == 7);
        activityResponseModelMap = activityResponseModels.stream().collect(Collectors.toMap(ActivityResponseModel::getName, activityResponseModel -> activityResponseModel));
        assert (activityResponseModelMap.containsKey("activity 1"));
        assert (activityResponseModelMap.get("activity 1").getCapacity() == 2);
        assert (activityResponseModelMap.containsKey("activity 3"));
        assert (activityResponseModelMap.get("activity 3").getCapacity() == 2);

        // verify passenger 1 is enrolled in travel package id 1
        travelPackagePassengerResponseModel = travelPackageController.travelPackagePassengerResponseModelApiResponse(1).getData();
        assert (travelPackagePassengerResponseModel.getPassengersEnrolled() == 1);
        assert (travelPackagePassengerResponseModel.getPassengers().size() == 1);
        assert (travelPackagePassengerResponseModel.getPassengers().get(0).getPassengerNumber() == 1);

        // signUp passenger 3(GOLD MEMBER) in activities [5] and travel package id 1
        // verify passenger 3 has balance = 31, costPaid for activity 3 = 9
        signUpRequestModel.setTravelPackageId(1);
        signUpRequestModel.setPassengerNumber(3);
        signUpRequestModel.setActivityIds(new ArrayList<>() {{
            add(5);
        }});
        signUpResponseModel = travelPackageController.signUp(signUpRequestModel).getData();
        assert (signUpResponseModel != null);
        assert (signUpResponseModel.getBalance().compareTo(new BigDecimal("31")) == 0);
        assert (!signUpResponseModel.getActivityResponseModels().isEmpty());
        signUpActivityResponseModelMap = signUpResponseModel.getActivityResponseModels().stream().collect(Collectors.toMap(SignUpResponseModel.ActivityResponseModel::getActivityId, activityResponseModel -> activityResponseModel));
        assert (signUpActivityResponseModelMap.size() == 1);
        assert (signUpActivityResponseModelMap.containsKey(5));
        assert (signUpActivityResponseModelMap.get(5).getCostPaid().compareTo(new BigDecimal("9")) == 0);

        // verify activities set has size 7 and activity 5 has capacity 1
        activityResponseModels = activityController.activityResponseModelApiResponse().getData();
        assert (activityResponseModels.size() == 7);
        activityResponseModelMap = activityResponseModels.stream().collect(Collectors.toMap(ActivityResponseModel::getName, activityResponseModel -> activityResponseModel));
        assert (activityResponseModelMap.containsKey("activity 5"));
        assert (activityResponseModelMap.get("activity 5").getCapacity() == 1);

        // verify passenger 1 & 3 is enrolled in travel package id 1
        travelPackagePassengerResponseModel = travelPackageController.travelPackagePassengerResponseModelApiResponse(1).getData();
        assert (travelPackagePassengerResponseModel.getPassengersEnrolled() == 2);
        assert (travelPackagePassengerResponseModel.getPassengers().size() == 2);
        Set<Integer> passengerNumbers = travelPackagePassengerResponseModel.getPassengers().stream().map(TravelPackagePassengerResponseModel.PassengerModel::getPassengerNumber).collect(Collectors.toSet());
        assert (passengerNumbers.size() == 2);
        assert (passengerNumbers.contains(1) && passengerNumbers.contains(3));

        // signUp passenger 4(PREMIUM MEMBER) in activities [5] and travel package id 1
        signUpRequestModel.setTravelPackageId(1);
        signUpRequestModel.setPassengerNumber(4);
        signUpRequestModel.setActivityIds(new ArrayList<>() {{
            add(5);
        }});
        signUpResponseModel = travelPackageController.signUp(signUpRequestModel).getData();
        assert (signUpResponseModel != null);
        assert (signUpResponseModel.getBalance().compareTo(BigDecimal.ZERO) == 0);
        assert (!signUpResponseModel.getActivityResponseModels().isEmpty());
        signUpActivityResponseModelMap = signUpResponseModel.getActivityResponseModels().stream().collect(Collectors.toMap(SignUpResponseModel.ActivityResponseModel::getActivityId, activityResponseModel -> activityResponseModel));
        assert (signUpActivityResponseModelMap.size() == 1);
        assert (signUpActivityResponseModelMap.containsKey(5));
        assert (signUpActivityResponseModelMap.get(5).getCostPaid().compareTo(BigDecimal.ZERO) == 0);

        // verify activities set has size 6 and activity 5 is not included
        activityResponseModels = activityController.activityResponseModelApiResponse().getData();
        assert (activityResponseModels.size() == 6);
        activityResponseModelMap = activityResponseModels.stream().collect(Collectors.toMap(ActivityResponseModel::getName, activityResponseModel -> activityResponseModel));
        assert (!activityResponseModelMap.containsKey("activity 5"));


        // verify passenger 1 & 3 is enrolled in travel package id 1
        travelPackagePassengerResponseModel = travelPackageController.travelPackagePassengerResponseModelApiResponse(1).getData();
        assert (travelPackagePassengerResponseModel.getPassengersEnrolled() == 3);
        assert (travelPackagePassengerResponseModel.getPassengers().size() == 3);
        passengerNumbers = travelPackagePassengerResponseModel.getPassengers().stream().map(TravelPackagePassengerResponseModel.PassengerModel::getPassengerNumber).collect(Collectors.toSet());
        assert (passengerNumbers.size() == 3);
        assert (passengerNumbers.contains(1) && passengerNumbers.contains(3) && passengerNumbers.contains(4));

        // signUp passenger 4(PREMIUM MEMBER) in activities [2] and travel package id 1
        // verify passenger limit reached error has occurred
        signUpRequestModel.setTravelPackageId(1);
        signUpRequestModel.setPassengerNumber(4);
        signUpRequestModel.setActivityIds(new ArrayList<>() {{
            add(2);
        }});
        assertThrows(Exception.class, () -> travelPackageController.signUp(signUpRequestModel), "Expected to throw exception as passenger limit reached");

        // signUp passenger 4(PREMIUM MEMBER) in activities [5] and travel package id 2
        // verify activity capacity reached error has occurred
        signUpRequestModel.setTravelPackageId(2);
        signUpRequestModel.setPassengerNumber(4);
        signUpRequestModel.setActivityIds(new ArrayList<>() {{
            add(5);
        }});
        assertThrows(Exception.class, () -> travelPackageController.signUp(signUpRequestModel), "Expected to throw exception as activity capacity reached");
    }
}
