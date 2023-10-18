package org.example.service.signup;

import jakarta.transaction.Transactional;
import org.example.domain.entity.Activity;
import org.example.domain.entity.Ledger;
import org.example.domain.entity.Passenger;
import org.example.domain.entity.TravelPackage;
import org.example.domain.repository.LedgerRepository;
import org.example.service.signup.SignUpActivity;
import org.example.utils.ApiException;
import org.example.utils.constant.ErrorCode;
import org.example.utils.constant.PassengerMembership;
import org.example.utils.model.SignUpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StandardPassengerSignUp implements SignUpActivity {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Override
    @Transactional
    public SignUpResponseModel signUp(TravelPackage travelPackage, List<Activity> activities, Passenger passenger) throws ApiException {
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

    @Override
    public PassengerMembership getPassengerMemberShip() {
        return PassengerMembership.STANDARD;
    }
}
