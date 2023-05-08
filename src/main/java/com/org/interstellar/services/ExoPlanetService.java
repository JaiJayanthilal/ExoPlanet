package com.org.interstellar.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.org.interstellar.model.ExoPlanetModel;
import com.org.interstellar.response.ExoPlanetResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExoPlanetService {

    @Value("${spring.downloadUrl}")
    private String url;

    /**
     * downloadData
     * This Method is to perform Download ACtion, this method downloads json Data from the below url
     * url = https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets
     *
     * @return
     * @throws Exception
     */
    public ExoPlanetResponse downloadData() throws Exception {
        if(null == url){ // spring.downloadUrl not available in properties file
            url = "https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();

        String st = response.body().replaceAll("\"HostStarTempK\": \"\",", "\"HostStarTempK\": 0,");
        st = st.replaceAll("\"RadiusJpt\": \"\",", "\"RadiusJpt\": 0,");
        st = st.replaceAll("\"DiscoveryYear\": \"\",", "\"DiscoveryYear\": 0,");
        JsonParser parser = new JsonParser();
        JsonArray jsonAray = (JsonArray) parser.parse(st);
        List<ExoPlanetModel> downloadedExoPlanetList = gson.fromJson(jsonAray, new TypeToken<List<ExoPlanetModel>>() {}.getType());
        ExoPlanetResponse exoPlanetResponse = calculateExoPlanetDetails(downloadedExoPlanetList);
        return exoPlanetResponse;
    }


    /**
     * calculateExoPlanetDetails
     * This Method Calculates ExoPlanet(s) status from the downloaded Json
     *
     * @param downloadedExoPlanetList
     * @return
     */
    public ExoPlanetResponse calculateExoPlanetDetails(List<ExoPlanetModel> downloadedExoPlanetList) {
        List<String> orphanedPlanets = downloadedExoPlanetList.stream().filter(x -> x.getTypeFlag().equals("3")).map(ExoPlanetModel::getPlanetIdentifier).collect(Collectors.toList());
        ExoPlanetModel exoPlanetOrbittingHottestStar = downloadedExoPlanetList.stream().max(Comparator.comparingDouble(ExoPlanetModel::getHostStarTempK)).get();
        String planetOrbittingHottestStar = exoPlanetOrbittingHottestStar.getPlanetIdentifier();
        Set<String> yearSet = new TreeSet<>();
        for (int i = 0; i < downloadedExoPlanetList.size(); i++) {
            yearSet.add(downloadedExoPlanetList.get(i).getDiscoveryYear());
        }
        List<String> groupedListString = new ArrayList<>();
        List<String> yearList = new ArrayList<>(yearSet);
        for (int i = 0; i < yearList.size(); i++) {
            String finalGroupEdValue = eachYearSMLPlanetsCount(yearList.get(i), downloadedExoPlanetList);
            groupedListString.add(finalGroupEdValue);
        }
        ExoPlanetResponse exoPlanetResponse = new ExoPlanetResponse();
        exoPlanetResponse.setOrphanPlanets(orphanedPlanets);
        exoPlanetResponse.setPlanetOrbittingHottestStar(planetOrbittingHottestStar);
        exoPlanetResponse.setDiscovered(groupedListString);
        return exoPlanetResponse;
    }


    /**
     * eachYearSMLPlanetsCount
     * This Method tells us the number of discovered (s)mall, (M)edium and (L)arge planets every year
     *
     * @param year
     * @param downloadedExoPlanetList
     * @return
     */
    public String eachYearSMLPlanetsCount(String year, List<ExoPlanetModel> downloadedExoPlanetList) {
        int small = 0;
        int medium = 0;
        int large = 0;

        List<ExoPlanetModel> listOfPlanetsInSpecificYear = downloadedExoPlanetList.stream().filter(x -> x.getDiscoveryYear().equals(year)).collect(Collectors.toList());
        for (int i = 0; i < listOfPlanetsInSpecificYear.size(); i++) {
            if (listOfPlanetsInSpecificYear.get(i).getRadiusJpt() < 1) {
                ++small;
            } else if (listOfPlanetsInSpecificYear.get(i).getRadiusJpt() > 1 && listOfPlanetsInSpecificYear.get(i).getRadiusJpt() < 2) {
                ++medium;
            } else {
                ++large;
            }

        }
        if (year.equals("0")) {
            return "Discovered Year is Unknown for " + small + " small planet(s), " + medium + " medium planet(s), and " + large + " large planet(s)";
        }
        return "In the year " + year + " we discovered " + small + " small planet(s), " + medium + " medium planet(s), and " + large + " large planet(s)";
    }
}
