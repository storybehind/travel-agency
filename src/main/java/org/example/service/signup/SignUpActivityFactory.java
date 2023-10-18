package org.example.service.signup;

import jakarta.annotation.PostConstruct;
import org.example.service.signup.SignUpActivity;
import org.example.utils.constant.PassengerMembership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignUpActivityFactory {

    @Autowired
    private List<SignUpActivity> serviceList;

    private Map<PassengerMembership, SignUpActivity> map = new HashMap<>();

    @PostConstruct
    private void init() {
        for (SignUpActivity signUpActivity : serviceList) {
            map.put(signUpActivity.getPassengerMemberShip(), signUpActivity);
        }
    }

    public SignUpActivity getService(PassengerMembership passengerMembership) {
        return map.get(passengerMembership);
    }
}
