package org.example.controller;

import org.example.service.TravelPackageService;
import org.example.utils.ApiException;
import org.example.utils.ApiResponse;
import org.example.utils.model.SignUpRequestModel;
import org.example.utils.model.SignUpResponseModel;
import org.example.utils.model.TravelPackagePassengerResponseModel;
import org.example.utils.model.TravelPackageResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel")
public class TravelPackageController {

    @Autowired
    private TravelPackageService travelPackageService;

    @PostMapping("/signup")
    public ApiResponse<SignUpResponseModel> signUp(@RequestBody SignUpRequestModel signUpRequestModel) throws ApiException {
        return new ApiResponse<>(travelPackageService.signUp(signUpRequestModel));
    }

    @GetMapping("{id}")
    public ApiResponse<TravelPackageResponseModel> getTravelPackageById(@PathVariable("id") int id) throws ApiException {
        return new ApiResponse<>(travelPackageService.getTravelPackageResponseModel(id));
    }

    @GetMapping("{id}/passengers")
    public ApiResponse<TravelPackagePassengerResponseModel> travelPackagePassengerResponseModelApiResponse(@PathVariable("id") int id) throws ApiException {
        return new ApiResponse<>(travelPackageService.getTravelPackagePassengerResponseModel(id));
    }
}
