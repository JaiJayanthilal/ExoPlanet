package com.org.interstellar.controller;

import com.org.interstellar.response.ExoPlanetResponse;
import com.org.interstellar.services.ExoPlanetService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class ExoPlanetController {

    @Autowired
    ExoPlanetService exoPlanetService;

    /**
     * This Method Creates an Endpoint to Calculate Exoplanet Status
     *
     * @return
     * @throws Exception
     */
    @GetMapping("exoPlanet")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = ExoPlanetResponse.class),
            @ApiResponse(code = 500, message = "ERROR")
    })
    public ExoPlanetResponse getExoPlanetDetails() throws Exception {

        ExoPlanetResponse exoPlanetResponse = exoPlanetService.downloadData();
        return exoPlanetResponse;
    }


}
