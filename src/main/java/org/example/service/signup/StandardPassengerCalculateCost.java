package org.example.service.signup;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

/**
 * StandardPassengerCalculateCost implements {@link CalculateCostActivity} for {@link PassengerMembership#STANDARD} membership
 */
@Service
public class StandardPassengerCalculateCost implements CalculateCostActivity {

    /**
     * Calculates cost for STANDARD passenger
     * Returns {@link List of {@link org.example.utils.model.SignUpResponseModel.ActivityResponseModel}}
     */
    @Override
    public List<SignUpResponseModel.ActivityResponseModel> calculateCost(List<Activity> activities) throws ApiException {
        List<SignUpResponseModel.ActivityResponseModel> activityResponseModels = new ArrayList<>();
        BigDecimal totalCostRequired = new BigDecimal("0");
        for (Activity activity : activities) {
            totalCostRequired = totalCostRequired.add(activity.getCost());
            SignUpResponseModel.ActivityResponseModel activityResponseModel = new SignUpResponseModel.ActivityResponseModel();
            activityResponseModel.setActivityId(activity.getId());
            activityResponseModel.setActivityName(activity.getName());
            activityResponseModel.setCostPaid(activity.getCost());
            activityResponseModels.add(activityResponseModel);
        }
        return activityResponseModels;
    }

    @Override
    public PassengerMembership getPassengerMemberShip() {
        return PassengerMembership.STANDARD;
    }
}
