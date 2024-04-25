package com.example.harkkatyolateantti;

public class EmplmntRateDataMngr {
    private static EmplmntRateDataMngr instance;


    private Double emplmntRate;

    private EmplmntRateDataMngr() {
    }

    public static synchronized EmplmntRateDataMngr getInstance() {
        if (instance == null) {
            instance = new EmplmntRateDataMngr();
        }
        return instance;
    }

    public void setEmplmntRate(Double emplmntRate) {
        this.emplmntRate = emplmntRate;
    }

    public Double getEmplmntRate() {
        return emplmntRate;
    }
}
