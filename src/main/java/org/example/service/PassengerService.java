package org.example.service;

import org.example.domain.entity.Ledger;
import org.example.domain.entity.Passenger;
import org.example.domain.repository.LedgerRepository;
import org.example.domain.repository.PassengerRepository;
import org.example.utils.ApiException;
import org.example.utils.constant.ErrorCode;
import org.example.utils.model.PassengerResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    @Transactional(readOnly = true)
    public PassengerResponseModel getPassengerResponseModel(int passengerNumber) throws ApiException {
        Optional<Passenger> passengerOptional = passengerRepository.findById(passengerNumber);
        Passenger passenger = passengerOptional.orElseThrow(() -> new ApiException(ErrorCode.INVALID_PASSENGER_IDENTIFIER));

        PassengerResponseModel passengerResponseModel = new PassengerResponseModel();
        passengerResponseModel.setPassengerNumber(passenger.getPassengerNumber());
        passengerResponseModel.setName(passenger.getName());
        passengerResponseModel.setBalance(passenger.getBalance());
        List<PassengerResponseModel.ActivityModel> activityModels = new ArrayList<>();
        List<Ledger> ledgers = ledgerRepository.findByPassenger(passenger);
        for (Ledger ledger : ledgers) {
            PassengerResponseModel.ActivityModel activityModel = new PassengerResponseModel.ActivityModel();
            activityModel.setActivityName(ledger.getActivity().getName());
            activityModel.setTravelPackageName(ledger.getTravelPackage().getName());
            activityModel.setPricePaid(ledger.getPricePaid());
            activityModels.add(activityModel);
        }
        passengerResponseModel.setActivities(activityModels);
        return passengerResponseModel;
    }
}
