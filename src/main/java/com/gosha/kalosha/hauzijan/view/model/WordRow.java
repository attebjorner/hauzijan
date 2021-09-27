package com.gosha.kalosha.hauzijan.view.model;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data @NoArgsConstructor @AllArgsConstructor
public class WordRow
{
    private String word;

    private String lemma;

    private String pos;

    private String grammar;

    public static WordRow fromDto(WordDto word)
    {
        return new WordRow(
                word.word(), word.lemma(), word.pos(),
                word.grammar().entrySet()
                        .stream()
                        .map(e -> e.getKey() + "=" + e.getValue() + "\n")
                        .collect(Collectors.joining())
        );
    }
}
