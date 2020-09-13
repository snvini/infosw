package com.vsoares.infosw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    public Planet (long id, String name, Long appearances){
        this.id = id;
        this.name = name;
        this.appearances = appearances;
    }

    @Id
    private long id;
    private String name;
    private String climate;
    private String terrain;
    private Long appearances;
}