package com.vsoares.infosw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "planets")
public class Planet {

    public Planet (String name){
        this.name = name;
    }

    public Planet ( String name, Long appearances){
        this.id = new ObjectId();
        this.name = name;
        this.appearances = appearances;
    }

    @Id
    private ObjectId id;
    private String name;
    private String climate;
    private String terrain;
    private Long appearances;

    public String getId(){
        return this.id.toString();
    }

}