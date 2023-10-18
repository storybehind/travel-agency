package org.example.utils.model;

import java.math.BigDecimal;
import java.util.List;

public class SignUpResponseModel {
    private List<ActivityResponseModel> activityResponseModels;
    private BigDecimal balance;

    public static class ActivityResponseModel {
        private int activityId;
        private String activityName;
        private BigDecimal costPaid;

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public BigDecimal getCostPaid() {
            return costPaid;
        }

        public void setCostPaid(BigDecimal costPaid) {
            this.costPaid = costPaid;
        }
    }

    public List<ActivityResponseModel> getActivityResponseModels() {
        return activityResponseModels;
    }

    public void setActivityResponseModels(List<ActivityResponseModel> activityResponseModels) {
        this.activityResponseModels = activityResponseModels;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
