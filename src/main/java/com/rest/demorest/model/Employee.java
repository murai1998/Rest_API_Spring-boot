package com.rest.demorest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;


public class Employee {
    @NotBlank
    private final String name;

    private final UUID id;
    @NotBlank
    private final String title;
    @NotBlank
    private final int  anniversary;

    public Employee(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("title") String title, @JsonProperty("anniversary") int  anniversary) {
        this.id = id;


        this.name = name;
        this.title = title;
        this. anniversary =  anniversary;
    }

    public int getAnniversary() {
        return anniversary;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return id;
    }
}
