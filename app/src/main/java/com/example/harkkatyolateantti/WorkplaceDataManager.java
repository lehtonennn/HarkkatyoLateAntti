package com.example.harkkatyolateantti;

import android.util.Log;

public class WorkplaceDataManager {
    private static WorkplaceDataManager instance;

    private Double workplaceData;

    private WorkplaceDataManager() {
    }

    public static synchronized WorkplaceDataManager getInstance() {
        if (instance == null) {
            instance = new WorkplaceDataManager();
        }
        return instance;
    }

    public void setWorkplaceData(Double workplaceData) {
        this.workplaceData = workplaceData;
    }

    public Double getWorkplaceData() {
        return workplaceData;
    }
}
