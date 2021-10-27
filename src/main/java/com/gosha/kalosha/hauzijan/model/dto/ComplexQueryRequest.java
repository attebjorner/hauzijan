package com.gosha.kalosha.hauzijan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplexQueryRequest
{
    private String lemma;

    private String pos;

    private Map<String, String> grammar;

    private String stingifiedGrammar;
}
