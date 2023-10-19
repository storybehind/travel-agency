package org.example.service.signup;

import org.example.domain.entity.Activity;
import org.example.domain.entity.Ledger;
import org.example.domain.entity.Passenger;
import org.example.domain.entity.TravelPackage;
import org.example.domain.repository.LedgerRepository;
import org.example.utils.ApiException;
import org.example.utils.constant.PassengerMembership;
import org.example.utils.model.SignUpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PremiumPassengerCalculateCost implements {@link CalculateCostActivity} for {@link PassengerMembership#PREMIUM} membership
 */
@Service
public class PremiumPassengerCalculateCost implements CalculateCostActivity {

    static final BigDecimal requiredCost = new BigDecimal("0");

    /**
     * Calculates cost for PREMIUM passenger
     * Returns {@link List of {@link org.example.utils.model.SignUpResponseModel.ActivityResponseModel}}
     */
    @Override
    public List<SignUpResponseModel.ActivityResponseModel> calculateCost(List<Activity> activities) throws ApiException {
        List<SignUpResponseModel.ActivityResponseModel> activityResponseModels = new ArrayList<>();
        for (Activity activity : activities) {
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
        return PassengerMembership.PREMIUM;
    }
}
