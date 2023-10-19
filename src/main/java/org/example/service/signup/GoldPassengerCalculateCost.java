package org.example.service.signup;

import org.example.domain.entity.Activity;
import org.example.domain.entity.Ledger;
import org.example.domain.entity.Passenger;
import org.example.domain.entity.TravelPackage;
import org.example.domain.repository.LedgerRepository;
import org.example.utils.ApiException;
import org.example.utils.constant.ErrorCode;
import org.example.utils.constant.PassengerMembership;
import org.example.utils.model.SignUpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * GoldPassengerCalculateCost implements {@link CalculateCostActivity} for {@link PassengerMembership#GOLD} membership
 */
@Service
public class GoldPassengerCalculateCost implements CalculateCostActivity {

    static final BigDecimal discountFraction = new BigDecimal("0.1");

    /**
     * Calculates cost for GOLD passenger
     * Returns {@link List of {@link org.example.utils.model.SignUpResponseModel.ActivityResponseModel}}
     */
    @Override
    public List<SignUpResponseModel.ActivityResponseModel> calculateCost(List<Activity> activities) throws ApiException {
        List<SignUpResponseModel.ActivityResponseModel> activityResponseModels = new ArrayList<>();
        BigDecimal totalCostRequired = new BigDecimal("0");

        for (Activity activity : activities) {
            BigDecimal discountCost = activity.getCost().multiply(discountFraction);
            BigDecimal requiredCost = activity.getCost().subtract(discountCost);
            totalCostRequired = totalCostRequired.add(requiredCost);
            SignUpResponseModel.ActivityResponseModel activityResponseModel = new SignUpResponseModel.ActivityResponseModel();
            activityResponseModel.setActivityId(activity.getId());
            activityResponseModel.setActivityName(activity.getName());
            activityResponseModel.setCostPaid(requiredCost);
            activityResponseModels.add(activityResponseModel);
        }
        return activityResponseModels;
    }

    @Override
    public PassengerMembership getPassengerMemberShip() {
        return PassengerMembership.GOLD;
    }
}
