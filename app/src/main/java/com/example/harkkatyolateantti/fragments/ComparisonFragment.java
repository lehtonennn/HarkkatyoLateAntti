package com.example.harkkatyolateantti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.harkkatyolateantti.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ComparisonFragment extends Fragment {

    private EditText editTextSearch;
    private TextView textViewKunta1Header;
    private TextView textViewKunta1Population;
    private TextView textViewKunta1Area;
    private TextView textViewKunta2Header;
    private TextView textViewKunta2Population;
    private TextView textViewKunta2Area;

    public ComparisonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_comparison, container, false);


        editTextSearch = rootView.findViewById(R.id.editTextSearch);
        textViewKunta1Header = rootView.findViewById(R.id.textViewKunta1Header);
        textViewKunta1Population = rootView.findViewById(R.id.textViewKunta1Population);
        textViewKunta1Area = rootView.findViewById(R.id.textViewKunta1Area);
        textViewKunta2Header = rootView.findViewById(R.id.textViewKunta2Header);
        textViewKunta2Population = rootView.findViewById(R.id.textViewKunta2Population);
        textViewKunta2Area = rootView.findViewById(R.id.textViewKunta2Area);


        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    performSearch(editTextSearch.getText().toString());
                }
            }
        });

        return rootView;
    }

    private void performSearch(String query) {
        String dummyJsonData = "{\"kunta1\": {\"nimi\": \"Helsinki\", \"väkiluku\": 655281, \"pinta-ala\": 715.49}, " +
                "\"kunta2\": {\"nimi\": \"Espoo\", \"väkiluku\": 290445, \"pinta-ala\": 528.03}}";

        try {
            JSONObject jsonData = new JSONObject(dummyJsonData);


            JSONObject kunta1 = jsonData.getJSONObject("kunta1");
            textViewKunta1Header.setText(kunta1.getString("nimi"));
            textViewKunta1Population.setText("Väkiluku: " + kunta1.getInt("väkiluku"));
            textViewKunta1Area.setText("Pinta-ala: " + kunta1.getDouble("pinta-ala") + " km²");


            JSONObject kunta2 = jsonData.getJSONObject("kunta2");
            textViewKunta2Header.setText(kunta2.getString("nimi"));
            textViewKunta2Population.setText("Väkiluku: " + kunta2.getInt("väkiluku"));
            textViewKunta2Area.setText("Pinta-ala: " + kunta2.getDouble("pinta-ala") + " km²");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}