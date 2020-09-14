package com.vsoares.infosw.service;

import com.vsoares.infosw.model.Planet;
import com.vsoares.infosw.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private SwapiService swapiService;

    @Override
    public Planet addPlanet(Planet planet) {
        if(planet.getName() == null){
            throw new NullPointerException("name");
        }
        planet.setName(planet.getName()
                        .replaceAll("[^a-zA-Z0-9\\s+]", "")
                        .toUpperCase()
                        .stripLeading()
                        .stripTrailing());
        planet.setId(planet.getName().hashCode());
        long appearances = swapiService.getAppearances(planet.getName());
        planet.setAppearances(appearances);
        planetRepository.save(planet);
        return planet;
    }

    @Override
    public List<Planet> listPlanets() {
        return  planetRepository.findAll();
    }

    @Override
    public Planet getByName(String name) {
        return planetRepository.findByName(name);
    }

    @Override
    public Planet getById(Long id) {
        Optional<Planet> planet = planetRepository.findById(id);
        if(planet.isPresent()){
            return planet.get();
        } else {
            return null;
        }
    }

    @Override
    public Boolean removePlanet(Long id) {
        Optional<Planet> planet = planetRepository.findById(id);
        if(planet.isPresent()){
            planetRepository.delete(planet.get());
            return true;
        } else {
            return false;
        }
    }
}
