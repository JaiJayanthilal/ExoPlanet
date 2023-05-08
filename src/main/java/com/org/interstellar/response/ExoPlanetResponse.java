package com.org.interstellar.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * ExoplanetCatalogue
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-07T11:36:53.887461975Z[GMT]")
public class ExoPlanetResponse {
    @JsonProperty("orphanPlanets")
    @Valid
    private List<String> orphanPlanets = null;

    @JsonProperty("planetOrbittingHottestStar")
    private String planetOrbittingHottestStar = null;

    @JsonProperty("discovered")
    @Valid
    private List<String> discovered = null;

    public ExoPlanetResponse orphanPlanets(List<String> orphanPlanets) {
        this.orphanPlanets = orphanPlanets;
        return this;
    }

    public ExoPlanetResponse addOrphanPlanetsItem(String orphanPlanetsItem) {
        if (this.orphanPlanets == null) {
            this.orphanPlanets = new ArrayList<String>();
        }
        this.orphanPlanets.add(orphanPlanetsItem);
        return this;
    }

    /**
     * Get orphanPlanets
     *
     * @return orphanPlanets
     **/
    @Schema(example = "[\"str1\",\"str2\",\"str3\"]", description = "")

    public List<String> getOrphanPlanets() {
        return orphanPlanets;
    }

    public void setOrphanPlanets(List<String> orphanPlanets) {
        this.orphanPlanets = orphanPlanets;
    }

    public ExoPlanetResponse planetOrbittingHottestStar(String planetOrbittingHottestStar) {
        this.planetOrbittingHottestStar = planetOrbittingHottestStar;
        return this;
    }

    /**
     * Get planetOrbittingHottestStar
     *
     * @return planetOrbittingHottestStar
     **/
    @Schema(example = "Str4", description = "")

    public String getPlanetOrbittingHottestStar() {
        return planetOrbittingHottestStar;
    }

    public void setPlanetOrbittingHottestStar(String planetOrbittingHottestStar) {
        this.planetOrbittingHottestStar = planetOrbittingHottestStar;
    }

    public ExoPlanetResponse grouped(List<String> grouped) {
        this.discovered = grouped;
        return this;
    }

    public ExoPlanetResponse addGroupedItem(String groupedItem) {
        if (this.discovered == null) {
            this.discovered = new ArrayList<String>();
        }
        this.discovered.add(groupedItem);
        return this;
    }

    /**
     * Get grouped
     *
     * @return grouped
     **/
    @Schema(example = "[\"str1\",\"str2\",\"str3\"]", description = "")

    public List<String> getDiscovered() {
        return discovered;
    }

    public void setDiscovered(List<String> discovered) {
        this.discovered = discovered;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExoPlanetResponse exoplanetCatalogue = (ExoPlanetResponse) o;
        return Objects.equals(this.orphanPlanets, exoplanetCatalogue.orphanPlanets) && Objects.equals(this.planetOrbittingHottestStar, exoplanetCatalogue.planetOrbittingHottestStar) && Objects.equals(this.discovered, exoplanetCatalogue.discovered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orphanPlanets, planetOrbittingHottestStar, discovered);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ExoplanetCatalogue {\n");

        sb.append("    orphanPlanets: ").append(toIndentedString(orphanPlanets)).append("\n");
        sb.append("    planetOrbittingHottestStar: ").append(toIndentedString(planetOrbittingHottestStar)).append("\n");
        sb.append("    grouped: ").append(toIndentedString(discovered)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
