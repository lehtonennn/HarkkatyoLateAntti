package com.example.harkkatyolateantti.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.harkkatyolateantti.MunicipalityData;
import com.example.harkkatyolateantti.MunicipalityDataRetriever;
import com.example.harkkatyolateantti.MunicipalityDataManager;
import com.example.harkkatyolateantti.R;
import com.example.harkkatyolateantti.WeatherData;
import com.example.harkkatyolateantti.WeatherDataManager;
import com.example.harkkatyolateantti.WeatherDataRetriever;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComparisonFragment extends Fragment {

    private TextView textViewKunta1Header;
    private TextView textViewKunta1Population;
    private TextView textWeather1;
    public ComparisonFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comparison, container, false);

        textViewKunta1Header = rootView.findViewById(R.id.textViewKunta1Header);
        textViewKunta1Population = rootView.findViewById(R.id.textViewKunta1Population);
        textWeather1 = rootView.findViewById(R.id.textWeather1);

        String municipalityName = MunicipalityDataManager.getInstance().getMunicipalityName();
        int population = MunicipalityDataManager.getInstance().getPopulation();

        textViewKunta1Header.setText(municipalityName);
        textViewKunta1Population.setText("Väkiluku: " + population);

        WeatherData weatherData = WeatherDataManager.getInstance().getWeatherData();

        if (weatherData != null) {
            textWeather1.setText(
                            "Sää nyt " + weatherData.getMain() + " (" + weatherData.getDescription() + ")\n" +
                            "Lämpötila: " + weatherData.getTemperatureInCelsius() + "C°\n" +
                            "Tuulennopeus: " + weatherData.getWindSpeed() +  "m/s\n"
            );
        } else {
            return null;
        }

        EditText editTextSearch = rootView.findViewById(R.id.editTextSearch);
        Button searchButton = rootView.findViewById(R.id.buttonSearch);
        TextView textViewKunta2Header = rootView.findViewById(R.id.textViewKunta2Header);
        TextView textViewKunta2Population = rootView.findViewById(R.id.textViewKunta2Population);
        TextView textWeather2 = rootView.findViewById(R.id.textWeather2);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String municipalityName = editTextSearch.getText().toString();
                MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
                WeatherDataRetriever wr = new WeatherDataRetriever();
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        MunicipalityData municipalityData = mr.getData(getContext(), municipalityName, true);
                        WeatherData weatherData = wr.getWeatherData(municipalityName);

                        if (municipalityData != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textViewKunta2Header.setText(municipalityName);
                                    textViewKunta2Population.setText("Väkiluku: " + municipalityData.getPopulation());
                                    textWeather2.setText("Sää nyt: " + weatherData.getMain() + " (" + weatherData.getDescription() + ")\n" +
                                            "Lämpötila: " + weatherData.getTemperatureInCelsius() + "C°\n" +
                                            "Tuulennopeus: " + weatherData.getWindSpeed() +  "m/s\n");
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Kuntaa ei löytynyt", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        return rootView;
    }
}

