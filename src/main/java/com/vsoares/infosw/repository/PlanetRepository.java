package com.vsoares.infosw.repository;

import com.vsoares.infosw.model.Planet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<Planet, ObjectId> {

    Planet findByName (String name);

}
