package com.org.interstellar.model;

import lombok.Data;

@Data
public class ExoPlanetModel {
    private String PlanetIdentifier;
    private String    TypeFlag;
    private String    PlanetaryMassJpt;
    private Double    RadiusJpt;
    private String    PeriodDays;
    private String    SemiMajorAxisAU;
    private String     Eccentricity;
    private String     PeriastronDeg;
    private String     LongitudeDeg;
    private String     AscendingNodeDeg;
    private String    InclinationDeg;
    private String     SurfaceTempK;
    private String     AgeGyr;
    private String     DiscoveryMethod;
    private String    DiscoveryYear;
    private String    LastUpdated;
    private String     RightAscension;
    private String     Declination;
    private String     DistFromSunParsec;
    private String    HostStarMassSlrMass;
    private String    HostStarRadiusSlrRad;
    private String    HostStarMetallicity;
    private Double    HostStarTempK;
    private String    HostStarAgeGyr;




}
