package com.org.interstellar.services;

import com.org.interstellar.model.ExoPlanetModel;
import com.org.interstellar.response.ExoPlanetResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExoPlanetServiceTest {

    @InjectMocks
    ExoPlanetService exoPlanetService;

    String responseBody() {
        String st = "[\n" +
                "  {\n" +
                "    \"PlanetIdentifier\": \"KOI-1843.03\",\n" +
                "    \"TypeFlag\": 0,\n" +
                "    \"PlanetaryMassJpt\": 0.0014,\n" +
                "    \"RadiusJpt\": 0.054,\n" +
                "    \"PeriodDays\": 0.1768913,\n" +
                "    \"SemiMajorAxisAU\": 0.0048,\n" +
                "    \"Eccentricity\": \"\",\n" +
                "    \"PeriastronDeg\": \"\",\n" +
                "    \"LongitudeDeg\": \"\",\n" +
                "    \"AscendingNodeDeg\": \"\",\n" +
                "    \"InclinationDeg\": 72,\n" +
                "    \"SurfaceTempK\": \"\",\n" +
                "    \"AgeGyr\": \"\",\n" +
                "    \"DiscoveryMethod\": \"transit\",\n" +
                "    \"DiscoveryYear\": 2012,\n" +
                "    \"LastUpdated\": \"13/07/15\",\n" +
                "    \"RightAscension\": \"19 00 03.14\",\n" +
                "    \"Declination\": \"+40 13 14.7\",\n" +
                "    \"DistFromSunParsec\": \"\",\n" +
                "    \"HostStarMassSlrMass\": 0.46,\n" +
                "    \"HostStarRadiusSlrRad\": 0.45,\n" +
                "    \"HostStarMetallicity\": 0,\n" +
                "    \"HostStarTempK\": 3584,\n" +
                "    \"HostStarAgeGyr\": \"\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"PlanetIdentifier\": \"KOI-1843.01\",\n" +
                "    \"TypeFlag\": 0,\n" +
                "    \"PlanetaryMassJpt\": \"\",\n" +
                "    \"RadiusJpt\": 0.114,\n" +
                "    \"PeriodDays\": 4.194525,\n" +
                "    \"SemiMajorAxisAU\": 0.039,\n" +
                "    \"Eccentricity\": \"\",\n" +
                "    \"PeriastronDeg\": \"\",\n" +
                "    \"LongitudeDeg\": \"\",\n" +
                "    \"AscendingNodeDeg\": \"\",\n" +
                "    \"InclinationDeg\": 89.38,\n" +
                "    \"SurfaceTempK\": \"\",\n" +
                "    \"AgeGyr\": \"\",\n" +
                "    \"DiscoveryMethod\": \"transit\",\n" +
                "    \"DiscoveryYear\": \"\",\n" +
                "    \"LastUpdated\": \"\",\n" +
                "    \"RightAscension\": \"19 00 03.14\",\n" +
                "    \"Declination\": \"+40 13 14.7\",\n" +
                "    \"DistFromSunParsec\": \"\",\n" +
                "    \"HostStarMassSlrMass\": 0.46,\n" +
                "    \"HostStarRadiusSlrRad\": 0.45,\n" +
                "    \"HostStarMetallicity\": 0,\n" +
                "    \"HostStarTempK\": 3584,\n" +
                "    \"HostStarAgeGyr\": \"\"\n" +
                "  }]";
        return st;
    }

    List<ExoPlanetModel> prepareExoPlanetModelList() {
        ExoPlanetModel ob = new ExoPlanetModel();
        ob.setPlanetIdentifier("KOI-1843.03");
        ob.setTypeFlag("0");
        ob.setRadiusJpt(0.054);
        ob.setHostStarTempK(3584d);
        ob.setDiscoveryYear("2012");

        ExoPlanetModel ob2 = new ExoPlanetModel();
        ob2.setPlanetIdentifier("PSO J318.5-22");
        ob2.setTypeFlag("3");
        ob2.setRadiusJpt(1.53);
        ob2.setHostStarTempK(0d);
        ob2.setDiscoveryYear("2013");

        ExoPlanetModel ob3 = new ExoPlanetModel();
        ob3.setPlanetIdentifier("V391 Peg b");
        ob3.setTypeFlag("0");
        ob3.setRadiusJpt(0d);
        ob3.setHostStarTempK(29300d);
        ob3.setDiscoveryYear("2007");
        List<ExoPlanetModel> exoPlanetModelList = new ArrayList<>();
        exoPlanetModelList.add(ob);
        exoPlanetModelList.add(ob2);
        exoPlanetModelList.add(ob3);
        return exoPlanetModelList;
    }


    @Test
    void downloadData() throws Exception {
        HttpRequest httpRequest = mock(HttpRequest.class);
        HttpClient client = mock(HttpClient.class);
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.body()).thenReturn(responseBody());
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);
        ExoPlanetResponse exoPlanetResponse = exoPlanetService.downloadData();
        Assertions.assertEquals(exoPlanetResponse.getOrphanPlanets().size(), 2);
    }

    @Test
    void calculateExoPlanetDetails() {
        ExoPlanetResponse exoPlanetResponse = exoPlanetService.calculateExoPlanetDetails(prepareExoPlanetModelList());
        Assertions.assertEquals(exoPlanetResponse.getOrphanPlanets().size(), 1);
        Assertions.assertEquals(exoPlanetResponse.getPlanetOrbittingHottestStar(), "V391 Peg b");
        Assertions.assertEquals(exoPlanetResponse.getDiscovered().size(), 3);
    }


}
