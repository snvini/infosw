package com.vsoares.infosw.repository;

import com.vsoares.infosw.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<Planet, Long> {

    Planet findByName (String name);

}
