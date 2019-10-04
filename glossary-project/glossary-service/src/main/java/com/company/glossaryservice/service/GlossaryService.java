package com.company.glossaryservice.service;

import com.company.glossaryservice.model.Definition;
import com.company.glossaryservice.util.feign.DefintionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GlossaryService {

    private DefintionClient client;

    @Autowired
    public GlossaryService(DefintionClient client) {
        this.client = client;
    }

    String [] censored = {"darn", "heck", "drat", "jerk", "butt"};

    public Definition createDefinition(Definition definition) {

        Arrays.stream(censored).forEach( word -> {
            if (definition.getTerm().equalsIgnoreCase(word)){
                throw new IllegalArgumentException("Word is not valid, censors blocked it");
            }
        });

        return client.createDefinition(definition);
    }

    public List<Definition> getAllByTerm(String term) {
        List<Definition> definitions = client.getAllByTerm(term);
        System.out.println(definitions);
        return definitions;
    }

}
