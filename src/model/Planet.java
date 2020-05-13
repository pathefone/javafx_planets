package model;

public class Planet {
    String planetName, composition, planetType, satellitesCount;
    int surfaceTemperature, density;

    public Planet(String planetName, String composition, String planetType,
                  String satellitesCount, int surfaceTemperature, int density ) {
        this.planetName = planetName;
        this.composition = composition;
        this.planetType =planetType;
        this.satellitesCount = satellitesCount;
        this.surfaceTemperature = surfaceTemperature;
        this.density = density;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getPlanetType() {
        return planetType;
    }

    public void setPlanetType(String planetType) {
        this.planetType = planetType;
    }

    public String getSatellitesCount() {
        return satellitesCount;
    }

    public void setSatellitesCount(String satellitesCount) {
        this.satellitesCount = satellitesCount;
    }

    public int getSurfaceTemperature() {
        return surfaceTemperature;
    }

    public void setSurfaceTemperature(int surfaceTemperature) {
        this.surfaceTemperature = surfaceTemperature;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }
}
