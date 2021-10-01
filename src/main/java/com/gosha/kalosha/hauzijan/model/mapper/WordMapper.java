package com.gosha.kalosha.hauzijan.model.mapper;

import com.gosha.kalosha.hauzijan.model.dto.WordDto;
import com.gosha.kalosha.hauzijan.model.entity.Word;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class WordMapper
{
    public static WordDto toDto(Word word)
    {
        Map<String, String> gram = (word.getGrammar().isEmpty())
                ? Map.of()
                : Arrays.stream(word.getGrammar().split("\\|"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(e -> e[0], e -> e[1], (e1, e2) -> e1));
        return new WordDto(word.getWord(), word.getLemma(), word.getPos(), gram);
    }
}
