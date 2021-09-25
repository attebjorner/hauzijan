package com.gosha.kalosha.hauzijan.view.model;

import lombok.Data;

import java.util.List;

@Data
public class ComplexQuery
{
    private String lemma;

    private String pos;

    private List<String> grammar;
}
