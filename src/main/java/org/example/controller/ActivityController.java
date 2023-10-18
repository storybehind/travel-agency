package org.example.controller;

import org.example.service.ActivityService;
import org.example.utils.ApiResponse;
import org.example.utils.model.ActivityResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping()
    public ApiResponse<List<ActivityResponseModel>> activityResponseModelApiResponse() {
        return new ApiResponse<>(activityService.getAllAvailableActivities());
    }
}
