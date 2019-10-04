package com.company.glossaryservice.util.feign;

import com.company.glossaryservice.model.Definition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "definition-service")
@RequestMapping("/definition")
public interface DefintionClient {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Definition createDefinition(@RequestBody Definition definition);
    @GetMapping("/term/{term}")
    @ResponseStatus(HttpStatus.OK)
    List<Definition> getAllByTerm(@PathVariable String term);

}
