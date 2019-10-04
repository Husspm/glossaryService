package com.company.glossaryservice.controller;

import com.company.glossaryservice.model.Definition;
import com.company.glossaryservice.service.GlossaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/glossary")
public class GlossaryController {

    @Autowired
    private GlossaryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Definition saveDefinition(@RequestBody @Valid Definition definition) {
        return service.createDefinition(definition);
    }

    @GetMapping("/term/{term}")
    @ResponseStatus(HttpStatus.OK)
    public List<Definition> getAllByTerm (@PathVariable String term) {
        return service.getAllByTerm(term);
    }

}
