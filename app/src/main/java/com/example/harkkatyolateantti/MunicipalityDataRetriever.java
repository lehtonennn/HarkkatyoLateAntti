package com.example.harkkatyolateantti;

import android.content.Context;
import android.util.Log;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MunicipalityDataRetriever {

    public MunicipalityData getData(Context context, String municipality, boolean onlyLatesYear) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode areas = null;

        try {
            areas = objectMapper.readTree(new URL("https://statfin.stat.fi/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        for (JsonNode node : areas.get("variables").get(1).get("values")) {
            values.add(node.asText());
        }
        for (JsonNode node : areas.get("variables").get(1).get("valueTexts")) {
            keys.add(node.asText());
        }

        HashMap<String, String> municipalityCodes = new HashMap<>();

        for(int i = 0; i < keys.size(); i++) {
            municipalityCodes.put(keys.get(i), values.get(i));
        }

        String code = null;


        code = null;
        code = municipalityCodes.get(municipality);


        try {
            URL url = new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/synt/statfin_synt_pxt_12dy.px");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JsonNode jsonInputString = objectMapper.readTree(context.getResources().openRawResource(R.raw.query));

            ((ObjectNode) jsonInputString.get("query").get(0).get("selection")).putArray("values").add(code);

            byte[] input = objectMapper.writeValueAsBytes(jsonInputString);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JsonNode municipalityData = objectMapper.readTree(response.toString());

            ArrayList<String> years = new ArrayList<>();
            ArrayList<String> populations = new ArrayList<>();

            int year = 0;
            int population = 0;

            for (JsonNode node : municipalityData.get("dimension").get("Vuosi").get("category").get("label")) {
                years.add(node.asText());
            }

            for (JsonNode node : municipalityData.get("value")) {
                populations.add(node.asText());
            }

            ArrayList<MunicipalityData> populationData = new ArrayList<>();

            for(int i = 0; i < years.size(); i++) {
                if(years.get(i).equals("2022")) {
                    year = Integer.parseInt(years.get(i));
                    population = Integer.parseInt(populations.get(i));
                    populationData.add(new MunicipalityData(year, population));
                    break;
                }
            }

            if (!populationData.isEmpty()) {
                MunicipalityData muniData = populationData.get(0);
                muniData.setName(municipality);
                Log.d("ANTTI ON VITUN HOMO", muniData.getYear() + " ja " + muniData.getPopulation());
            } else {
                Log.d("ANTTI ON VITUN HOMO", "Dataa ei löytynyt vuodelle 2022");
            }
            MunicipalityData muniData = new MunicipalityData(year, population);
            muniData.setName(municipality);

            return muniData;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
