package com.example.harkkatyolateantti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private EditText editMunicipalityId;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editMunicipalityId = findViewById(R.id.editMunicipalityId);


    }

    public void onFindBtnClick(View view) {

        WeatherDataRetriever wr = new WeatherDataRetriever();
        String municipalityName = editMunicipalityId.getText().toString();
        String location = editMunicipalityId.getText().toString();
        MunicipalityDataRetriever mr = new MunicipalityDataRetriever();

        MunicipalityDataManager dataManager = MunicipalityDataManager.getInstance();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                MunicipalityData municipalityData = mr.getData(MainActivity.this, location, true);

                if (municipalityData == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Kuntaa ei löytynyt.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                WeatherData weatherData = wr.getWeatherData(location);
                WeatherDataManager weatherDataManager = WeatherDataManager.getInstance();
                weatherDataManager.setWeatherData(weatherData);

                DevelopPopulRetriever pr = new DevelopPopulRetriever();
                ArrayList<PopulDevelopData> populationDataList = pr.getData(MainActivity.this, location);

                EmplmntRateDataRetriever er = new EmplmntRateDataRetriever();
                ArrayList<EmplmntRateData> emplmntRateDataList = er.getData(MainActivity.this, location);

                EmplmntRateDataMngr emplmntRateMngr = EmplmntRateDataMngr.getInstance();
                if (emplmntRateDataList != null && !emplmntRateDataList.isEmpty()) {
                    emplmntRateMngr.setEmplmntRate(emplmntRateDataList.get(0).getEmplmntRate());
                }

                PopulDevelopMngr populationManager = PopulDevelopMngr.getInstance();
                if (populationDataList != null && !populationDataList.isEmpty()) {
                    populationManager.setPopulationDevelopment(populationDataList.get(0).getPopulationDevelopment());
                }

                WorkplaceDataRetriever workplaceRetriever = new WorkplaceDataRetriever();
                ArrayList<WorkplaceData> workplaceDataList = workplaceRetriever.getData(MainActivity.this, location);
                if (workplaceDataList != null && !workplaceDataList.isEmpty()) {
                    WorkplaceDataManager.getInstance().setWorkplaceData(workplaceDataList.get(0).getWorkplaceData());
                }


                dataManager.setMunicipalityData(municipalityName, municipalityData.getPopulation());

                Intent intent = new Intent(MainActivity.this, FragmentActivity.class);

                startActivity(intent);
            }
        });

    }
}