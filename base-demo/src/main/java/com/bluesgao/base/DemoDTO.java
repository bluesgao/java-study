package com.bluesgao.base;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class DemoDTO {
    private String name;
    private Integer alexa;
    private Map<String, String> sites;

    public DemoDTO(String name, Integer alexa) {
        this.name = name;
        this.alexa = alexa;
    }

    public DemoDTO(String name, Integer alexa, Map<String, String> sites) {
        this.name = name;
        this.alexa = alexa;
        this.sites = sites;
    }
}
