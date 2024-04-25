package com.example.harkkatyolateantti;

public class PopulDevelopMngr {
    private static PopulDevelopMngr instance;

    private int populationDevelopment;

    private PopulDevelopMngr() {
    }

    public static synchronized PopulDevelopMngr getInstance() {
        if (instance == null) {
            instance = new PopulDevelopMngr();
        }
        return instance;
    }

    public void setPopulationDevelopment(int populationDevelopment) {
        this.populationDevelopment = populationDevelopment;
    }

    public int getPopulationDevelopment() {
        return populationDevelopment;
    }
}
