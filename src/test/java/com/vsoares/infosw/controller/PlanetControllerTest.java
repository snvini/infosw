package com.vsoares.infosw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsoares.infosw.model.Planet;
import com.vsoares.infosw.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    void postAndDeletePlanetSuccessTest()throws Exception {
        Long correctMovieAppearences = 5L;

        Planet mockPlanet = new Planet("Tatooine");
        mockMvc.perform(post("/planets/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockPlanet)))
                .andExpect(status().isOk());

        Planet planet = planetRepository.findByName("TATOOINE");
        assertThat(planet.getAppearances()).isEqualTo(correctMovieAppearences);
        mockMvc.perform(delete("/planets/{id}", planet.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        planet = planetRepository.findByName("TATOOINE");
        assertThat(planet == null);
    }

    @Test
    void postAndGetByIDSuccessTest()throws Exception {
        Planet mockPlanet = new Planet("Tatooine");

        mockMvc.perform(post("/planets/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockPlanet)))
                .andExpect(status().isOk());

        Planet planet = planetRepository.findByName("TATOOINE");

        mockMvc.perform(get("/planets/{id}", planet.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("TATOOINE"));
    }

    @Test
    void postAndGetByNameSuccessTest()throws Exception {
        String  name = "Tatooine";
        Planet mockPlanet = new Planet(name);

        mockMvc.perform(post("/planets/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockPlanet)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/planets/?name="+name)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    void getByIDFailTest()throws Exception {
        mockMvc.perform(get("/planets/abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void postFailTest()throws Exception {
        Planet mockPlanet = new Planet();
        mockMvc.perform(post("/planets/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockPlanet)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteFailTest()throws Exception {
        mockMvc.perform(delete("/planets/{id}", "abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
