package com.example.harkkatyolateantti;

import android.util.Log;

public class MunicipalityDataManager {
    private static MunicipalityDataManager instance;

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
        return municipalityName;
    }

    public int getPopulation() {
        return population;
    }
}

