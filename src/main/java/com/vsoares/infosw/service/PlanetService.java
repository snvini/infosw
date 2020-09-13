package com.vsoares.infosw.service;

import com.vsoares.infosw.model.Planet;

import java.util.List;

public interface PlanetService {

    void addPlanet(Planet planet);
    List<Planet> listPlanets();
    Planet getByName(String name);
    Planet getById(Long id);
    Boolean removePlanet(Long id);
}
