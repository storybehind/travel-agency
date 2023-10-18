package org.example.service.signup;

import org.example.domain.entity.Activity;
import org.example.domain.entity.Passenger;
import org.example.domain.entity.TravelPackage;
import org.example.utils.ApiException;
import org.example.utils.constant.PassengerMembership;
import org.example.utils.model.SignUpResponseModel;

import java.util.List;
import java.util.Set;

public interface SignUpActivity {

    SignUpResponseModel signUp(TravelPackage travelPackage, List<Activity> activities, Passenger passenger) throws ApiException;

    PassengerMembership getPassengerMemberShip();

}
