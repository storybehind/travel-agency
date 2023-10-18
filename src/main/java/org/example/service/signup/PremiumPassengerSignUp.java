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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PremiumPassengerSignUp implements SignUpActivity {

    @Autowired
    private LedgerRepository ledgerRepository;

    static BigDecimal requiredCost = new BigDecimal("0");

    @Override
    public SignUpResponseModel signUp(TravelPackage travelPackage, List<Activity> activities, Passenger passenger) throws ApiException {
        List<SignUpResponseModel.ActivityResponseModel> activityResponseModels = new ArrayList<>();
        for (Activity activity : activities) {
            SignUpResponseModel.ActivityResponseModel activityResponseModel = new SignUpResponseModel.ActivityResponseModel();
            activityResponseModel.setActivityId(activity.getId());
            activityResponseModel.setActivityName(activity.getName());
            activityResponseModel.setCostPaid(requiredCost);
            activityResponseModels.add(activityResponseModel);
        }

        travelPackage.addPassenger(passenger);
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
        return PassengerMembership.PREMIUM;
    }
}
