package com.vsoares.infosw.service;

import com.vsoares.infosw.model.Planet;
import com.vsoares.infosw.repository.PlanetRepository;
import com.vsoares.infosw.utils.StringUtils;
import org.bson.types.ObjectId;
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
    public Planet addPlanet(Planet newPlanet) {
        if (newPlanet.getName() == null) {
            throw new NullPointerException("name");
        }

        String name = StringUtils.normalizeName(newPlanet.getName());

        Planet planet = planetRepository.findByName(name);
        if (planet != null) {
            planet.setClimate(newPlanet.getClimate());
            planet.setTerrain(newPlanet.getTerrain());
        } else {
            planet = newPlanet;
            planet.setName(name);
            planet.createId();
        }
        long appearances = swapiService.getAppearances(name);
        planet.setAppearances(appearances);
        planetRepository.save(planet);
        return planet;
    }

    @Override
    public List<Planet> listPlanets() {
        return planetRepository.findAll();
    }

    @Override
    public Planet getByName(String name) {
        name = StringUtils.normalizeName(name);
        return planetRepository.findByName(name);
    }

    @Override
    public Planet getById(String id) {
        if (ObjectId.isValid(id)) {
            Optional<Planet> planet = planetRepository.findById(new ObjectId(id));
            if (planet.isPresent()) {
                return planet.get();
            }
        }
        return null;
    }

    @Override
    public Boolean removePlanet(String id) {
        if (ObjectId.isValid(id)) {
            Optional<Planet> planet = planetRepository.findById(new ObjectId(id));
            if (planet.isPresent()) {
                planetRepository.delete(planet.get());
                return true;
            }
        }
        return false;
    }
}
