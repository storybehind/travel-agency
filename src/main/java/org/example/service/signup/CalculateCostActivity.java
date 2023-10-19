package org.example.service.signup;

import org.example.domain.entity.Activity;
import org.example.domain.entity.Passenger;
import org.example.domain.entity.TravelPackage;
import org.example.utils.ApiException;
import org.example.utils.constant.PassengerMembership;
import org.example.utils.model.SignUpResponseModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * CalculateCostActivity for different passenger membership
 */
public interface CalculateCostActivity {

    List<SignUpResponseModel.ActivityResponseModel> calculateCost(List<Activity> activities) throws ApiException;

    PassengerMembership getPassengerMemberShip();

}
