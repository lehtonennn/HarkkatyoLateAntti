package com.example.harkkatyolateantti;

import android.util.Log;

public class MunicipalityDataManager {
    private static MunicipalityDataManager instance;

    private static final String TAG = "MunicipalityDataManager";
    private String municipalityName;
    private int population;

    private MunicipalityDataManager() {
    }

    public static synchronized MunicipalityDataManager getInstance() {
        if (instance == null) {
            instance = new MunicipalityDataManager();
        }
        return instance;
    }

    public void setMunicipalityData(String municipalityName, int population) {
        this.municipalityName = municipalityName;
        this.population = population;
    }

    public String getMunicipalityName() {
        Log.d(TAG, "getMunicipalityName: " + municipalityName);
        return municipalityName;
    }

    public int getPopulation() {
        Log.d(TAG, "getPopulation: " + population);
        return population;
    }
}

