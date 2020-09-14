package com.vsoares.infosw.service;

import com.vsoares.infosw.model.Planet;

import java.util.List;

public interface PlanetService {

    Planet addPlanet(Planet planet);
    List<Planet> listPlanets();
    Planet getByName(String name);
    Planet getById(String id);
    Boolean removePlanet(String id);
}
