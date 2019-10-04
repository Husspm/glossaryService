package com.company.glossaryservice.controller;

import com.company.glossaryservice.model.Definition;
import com.company.glossaryservice.service.GlossaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class GlossaryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GlossaryService service;

    Definition definition, definition2;

    @Before
    public void setUp() throws Exception {

        definition = new Definition();
        definition2 = new Definition();

        definition.setTerm("term");
        definition.setDefinition("desk");
        definition.setId(1);

        definition2.setTerm("term");
        definition2.setDefinition("desk");

    }

    private String asJsonString(final Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }

    @Test
    public void testSaveDefinition() throws Exception {

        when(service.createDefinition(definition2)).thenReturn(definition);

        mockMvc.perform(post("/glossary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(definition2))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(definition)));

    }

    @Test
    public void testGetAllByTerm() throws Exception {

        List<Definition> allTerms = new ArrayList<>();

        allTerms.add(definition);
        when(service.getAllByTerm("term")).thenReturn(allTerms);

        mockMvc.perform(get("/glossary/term/term"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(allTerms)));

    }

    @Test
    public void testCensoredWordShouldFail() throws Exception {

        definition2.setTerm("darn");

        when(service.createDefinition(definition2)).thenThrow(new IllegalArgumentException("That word is censored"));

        mockMvc.perform(post("/glossary")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(definition2)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}