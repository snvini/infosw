package com.vsoares.infosw.controller;

import com.vsoares.infosw.model.Planet;
import com.vsoares.infosw.service.PlanetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @GetMapping(value = "/")
    public List<Planet> getPlanets(@RequestParam(required = false) String name) {
        if(name != null){
            name = name.toUpperCase();
            Planet planet = planetService.getByName(name);
            List<Planet> results = new ArrayList<Planet>();
            results.add(planet);
            return results;
        }
            return planetService.listPlanets();
    }

    @GetMapping(value = "/{param}")
    public Planet getById(@PathVariable String param) {
        try {
            Planet planet = planetService.getById(param);
            if(planet != null){
                return planet;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Não foi possível encontrar o planeta com id " + param);
            }
        } catch(NumberFormatException nfe) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Não foi possível converter o parametro " + param + " em ID");
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> saveOrUpdatePlanet(@RequestBody Planet planet) {
        try{
            planetService.addPlanet(planet);
            return new ResponseEntity("Planeta adicionado com sucesso!", HttpStatus.OK);
        } catch (NullPointerException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Para postar um planeta é necessário definir o 'name'");
        }
    }

    @DeleteMapping(value = "/{param}")
    public ResponseEntity<?> deletePlanet(@PathVariable String param) {
        try {
            boolean success = planetService.removePlanet(param);
            if(success){
                return new ResponseEntity("Planeta removido com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity("Planeta não encontrado.", HttpStatus.OK);
            }
        } catch(NumberFormatException nfe) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Não foi possível converter o parametro " + param + " em ID");
        }
    }
}
