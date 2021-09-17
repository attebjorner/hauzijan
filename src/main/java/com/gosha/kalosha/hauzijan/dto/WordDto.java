package com.gosha.kalosha.hauzijan.dto;

import java.util.Map;

public record WordDto(
        String word,
        String lemma,
        String pos,
        Map<String, String> grammar
)
{
}
