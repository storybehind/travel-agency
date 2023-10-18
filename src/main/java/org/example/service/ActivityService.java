package org.example.service;

import org.example.domain.entity.Activity;
import org.example.domain.repository.ActivityRepository;
import org.example.utils.model.ActivityResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<ActivityResponseModel> getAllAvailableActivities() {
        List<Activity> activities = activityRepository.findAll();
        List<Activity> availableActivities = activities.stream().filter((activity -> activity.getCapacity() > 0)).collect(Collectors.toList());
        List<ActivityResponseModel> activityResponseModels = new ArrayList<>();
        for (Activity activity : availableActivities) {
            ActivityResponseModel activityResponseModel = new ActivityResponseModel();
            activityResponseModel.setName(activity.getName());
            activityResponseModel.setDescription(activity.getDescription());
            activityResponseModel.setCost(activity.getCost());
            activityResponseModel.setCapacity(activity.getCapacity());
            activityResponseModels.add(activityResponseModel);
        }
        return activityResponseModels;
    }
}
