package org.example.service.signup;

import jakarta.annotation.PostConstruct;
import org.example.utils.constant.PassengerMembership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory class to get instance of concrete {@link CalculateCostActivity} based on {@link PassengerMembership}
 */
@Service
public class SignUpActivityFactory {

    @Autowired
    private List<CalculateCostActivity> serviceList;

    private Map<PassengerMembership, CalculateCostActivity> map = new HashMap<>();

    @PostConstruct
    private void init() {
        for (CalculateCostActivity signUpActivity : serviceList) {
            map.put(signUpActivity.getPassengerMemberShip(), signUpActivity);
        }
    }

    /**
     * Returns concrete {@link CalculateCostActivity} based on {@link PassengerMembership}
     */
    public CalculateCostActivity getService(PassengerMembership passengerMembership) {
        return map.get(passengerMembership);
    }
}
