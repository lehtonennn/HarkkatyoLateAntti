package com.example.harkkatyolateantti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.harkkatyolateantti.fragments.BasicInformationFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private ArrayList<MunicipalityData> dataList = new ArrayList<>();

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

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        dataList = new ArrayList<>();


    }

    public void onFindBtnClick(View view) {

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
                    MunicipalityDataManager dataManager = MunicipalityDataManager.getInstance();
                    dataManager.setMunicipalityData(municipalityName, municipalityData.getPopulation());
                }
                Log.d("HOMO", String.valueOf(municipalityData.getPopulation()));

                Log.d("MainActivity", "Municipality Name: " + municipalityName);
                Log.d("MainActivity", "Location: " + location);

                Log.d("LUT", "Data haettu");
                dataManager.setMunicipalityData(municipalityName, municipalityData.getPopulation());

                Intent intent = new Intent(MainActivity.this, FragmentActivity.class);

                startActivity(intent);
            }
        });

    }
}