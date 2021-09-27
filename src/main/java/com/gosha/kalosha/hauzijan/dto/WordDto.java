package com.gosha.kalosha.hauzijan.dto;

import com.gosha.kalosha.hauzijan.model.Word;

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
    public static WordDto fromWord(Word word)
    {
        Map<String, String> gram = (word.getGrammar().isEmpty())
                ? Map.of()
                : Arrays.stream(word.getGrammar().split("\\|"))
                        .map(s -> s.split("="))
                        .collect(Collectors.toMap(e -> e[0], e -> e[1], (e1, e2) -> e1));
        return new WordDto(word.getWord(), word.getLemma(), word.getPos(), gram);
    }
}
