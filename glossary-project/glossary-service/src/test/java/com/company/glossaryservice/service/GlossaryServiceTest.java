package com.company.glossaryservice.service;

import com.company.glossaryservice.model.Definition;
import com.company.glossaryservice.util.feign.DefintionClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlossaryServiceTest {

    DefintionClient client;
    GlossaryService service;

    private Definition definition;

    @Before
    public void setUp() throws Exception {

        setupFeignMock();
        service = new GlossaryService(client);

        definition = new Definition();
        definition.setTerm("term");
        definition.setDefinition("deff");

    }

    private void setupFeignMock() {
        client = mock(DefintionClient.class);

        Definition definition = new Definition();
        definition.setTerm("term");
        definition.setDefinition("deff");
        definition.setId(1);

        Definition definition2 = new Definition();
        definition2.setTerm("term");
        definition2.setDefinition("deff");

        Definition definition3 = new Definition();
        definition3.setTerm("term");
        definition3.setDefinition("deff 2");
        definition3.setId(2);

        List<Definition> definitions = new ArrayList<>();

        definitions.add(definition);
        definitions.add(definition3);

        doReturn(definition).when(client).createDefinition(definition2);
        doReturn(definitions).when(client).getAllByTerm("term");

    }

    @Test
    public void testCreateDefinition() {
        definition = service.createDefinition(definition);
        assertEquals(1, (int)definition.getId());
    }

    @Test
    public void testGetAllByTerm() {
        assertEquals(2, service.getAllByTerm("term").size());
    }

    @Test
    public void testServiceWillRejectCensoredWords() {
        definition.setTerm("jerk");
        try {
            definition = service.createDefinition(definition);
        } catch (IllegalArgumentException e) {
            assertEquals("Word is not valid, censors blocked it", e.getMessage());
        }
    }

}