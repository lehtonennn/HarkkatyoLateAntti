package com.example.harkkatyolateantti.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harkkatyolateantti.EmplmntRateDataMngr;
import com.example.harkkatyolateantti.MunicipalityDataManager;
import com.example.harkkatyolateantti.PopulDevelopMngr;
import com.example.harkkatyolateantti.R;
import com.example.harkkatyolateantti.WeatherData;
import com.example.harkkatyolateantti.WeatherDataManager;
import com.example.harkkatyolateantti.WorkplaceDataManager;

public class BasicInformationFragment extends Fragment {

    private static final String ARG_MUNICIPALITY_NAME = "municipality_name";
    private static final String ARG_POPULATION = "population";

    private String municipalityName;
    private int population;
    public TextView textViewMunicipalityName;
    public TextView textActualPopulationChange;
    public TextView textActualPopulation;
    public TextView textActualWeather;


    public BasicInformationFragment() {
    }


    public static BasicInformationFragment newInstance(String municipalityName, int population, WeatherData weatherData) {
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

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basic_information, container, false);

        textViewMunicipalityName = rootView.findViewById(R.id.textViewMunicipalityName);
        textActualPopulation = rootView.findViewById(R.id.textActualPopulation);
        textActualWeather = rootView.findViewById(R.id.textActualWeather);
        textActualPopulationChange = rootView.findViewById(R.id.textActualPopulationChange);

        String municipalityName = MunicipalityDataManager.getInstance().getMunicipalityName();
        int population = MunicipalityDataManager.getInstance().getPopulation();


        WeatherData weatherData = WeatherDataManager.getInstance().getWeatherData();
        PopulDevelopMngr populationManager = PopulDevelopMngr.getInstance();
        int populationDevelopmentData = populationManager.getPopulationDevelopment();

        EmplmntRateDataMngr emplmntRateDataMngr = EmplmntRateDataMngr.getInstance();
        double emplmntRateData = emplmntRateDataMngr.getEmplmntRate();

        WorkplaceDataManager workplaceDataManager = WorkplaceDataManager.getInstance();
        double workplaceData = workplaceDataManager.getWorkplaceData();

        textActualPopulationChange.setText("Väestönmuutos: " + populationDevelopmentData + "\n"
                + "Työllisyysaste: " + emplmntRateData + "\n"
                + "Työpaikkaomavaraisuus: " + workplaceData);


        if (weatherData != null) {
            textActualWeather.setText(
                    weatherData.getName() + "\n" +
                            "Sää nyt " + weatherData.getMain() + " (" + weatherData.getDescription() + ")\n" +
                            "Lämpötila: " + weatherData.getTemperatureInCelsius() + "C°\n" +
                            "Tuulennopeus: " + weatherData.getWindSpeed() +  "m/s\n"
            );
        } else {
            Log.d("Fragment", "Weather data is null");
            return null;
        }

        if (municipalityName != null && population != 0) {
            updateData(municipalityName, population);
        } else {

            rootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateData(municipalityName, population);
                }
            }, 100);
        }

        return rootView;
    }


    public void updateData(String municipalityName, int population) {
        textViewMunicipalityName.setText(municipalityName);
        textActualPopulation.setText(String.valueOf(population));

    }
}