package org.example.controller;

import org.example.service.PassengerService;
import org.example.utils.ApiException;
import org.example.utils.ApiResponse;
import org.example.utils.model.PassengerResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping("{id}")
    public ApiResponse<PassengerResponseModel> passengerResponseModelApiResponse(@PathVariable("id") int id) throws ApiException {
        return new ApiResponse<>(passengerService.getPassengerResponseModel(id));
    }

}
