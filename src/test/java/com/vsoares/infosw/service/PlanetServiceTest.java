package com.vsoares.infosw.service;

import com.vsoares.infosw.model.Planet;
import com.vsoares.infosw.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetServiceImpl planetService;

    @Mock
    private SwapiServiceImpl swapiService;

    @Test
    void addPlanetSucess(){
        final Planet planet = new Planet( "planeta",3l);
        when(planetRepository.save(any(Planet.class))).thenReturn(new Planet());
        Planet created = planetService.addPlanet(planet);
        assertThat(created.getId()).isEqualTo(planet.getId());
    }

    @Test
    void addPlanetFail(){
        final Planet planet = new Planet();
        assertThrows(NullPointerException.class,() ->{
            planetService.addPlanet(planet);
        });
        verify(planetRepository, never()).save(any(Planet.class));
    }

    @Test
    void listPlanetsTest(){
        List<Planet> planets = new ArrayList<>();
        planets.add(new Planet("a"));
        planets.add(new Planet("b"));
        planets.add(new Planet("c"));
        for(Planet p : planets){
            planetRepository.save(p);
        }
        given(planetRepository.findAll()).willReturn(planets);
        List<Planet> expected = planetService.listPlanets();
        assertEquals(expected,planets);
    }

    @Test
    void getByNameTest(){
        String name = "planeta";
        Planet planeta = new Planet(name);
        planeta = planetService.addPlanet(planeta);
        given(planetRepository.findByName(name)).willReturn(planeta);
        Planet expected = planetService.getByName(name);
        assertThat(expected).isNotNull();
    }
}
