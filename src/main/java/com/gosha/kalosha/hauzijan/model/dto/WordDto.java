package com.gosha.kalosha.hauzijan.model.dto;

import com.gosha.kalosha.hauzijan.model.entity.Word;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public record WordDto(
        String word,
        String lemma,
        String pos,
        Map<String, String> grammar
)
{
}
