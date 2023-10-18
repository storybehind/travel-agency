package org.example.controller;

import org.example.service.TravelPackageService;
import org.example.utils.ApiException;
import org.example.utils.ApiResponse;
import org.example.utils.model.SignUpRequestModel;
import org.example.utils.model.SignUpResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TravelPackageController {

    @Autowired
    private TravelPackageService travelPackageService;

    @PostMapping("/signup")
    public ApiResponse<SignUpResponseModel> signUp(@RequestBody SignUpRequestModel signUpRequestModel) throws ApiException {
        return new ApiResponse<>(travelPackageService.signUp(signUpRequestModel));
    }
}
