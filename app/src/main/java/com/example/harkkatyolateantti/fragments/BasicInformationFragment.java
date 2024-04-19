package com.example.harkkatyolateantti.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harkkatyolateantti.MunicipalityData;
import com.example.harkkatyolateantti.MunicipalityDataManager;
import com.example.harkkatyolateantti.R;

public class BasicInformationFragment extends Fragment {

    private static final String ARG_MUNICIPALITY_NAME = "municipality_name";
    private static final String ARG_POPULATION = "population";

    private String municipalityName;
    private int population;
    private String mParam1;
    private String mParam2;

    public TextView textViewMunicipalityName;
    public TextView textActualArea;
    public TextView textViewPopulation;
    public TextView textWeather;
    public TextView textActualPopulation;
    public TextView textActualWeather;
    public TextView textViewArea;



    public BasicInformationFragment() {
    }


    public static BasicInformationFragment newInstance(String municipalityName, int population) {
        BasicInformationFragment fragment = new BasicInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MUNICIPALITY_NAME, municipalityName);
        args.putInt(ARG_POPULATION, population);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            municipalityName = getArguments().getString(ARG_MUNICIPALITY_NAME);
            population = getArguments().getInt(ARG_POPULATION);
            MunicipalityDataManager.getInstance().setMunicipalityData(municipalityName, population);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basic_information, container, false);

        textViewMunicipalityName = rootView.findViewById(R.id.textViewMunicipalityName);
        textActualPopulation = rootView.findViewById(R.id.textActualPopulation);

        String municipalityName = MunicipalityDataManager.getInstance().getMunicipalityName();
        int population = MunicipalityDataManager.getInstance().getPopulation();


        if (municipalityName != null && population != 0) {
            // Jos tiedot ovat jo saatavilla, päivitä näkymä
            updateData(municipalityName, population);
        } else {
            // Jos tiedot eivät ole vielä saatavilla, odota ja päivitä sitten
            rootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateData(municipalityName, population);
                }
            }, 100); // Odota 100 millisekuntia ja yritä sitten uudelleen
        }

        return rootView;
    }

    public void updateData(String municipalityName, int population) {
        textViewMunicipalityName.setText(municipalityName);
        textActualPopulation.setText(String.valueOf(population));
    }
}