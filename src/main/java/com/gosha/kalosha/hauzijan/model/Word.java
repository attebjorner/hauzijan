package com.gosha.kalosha.hauzijan.model;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "wordforms")
@Data
public class Word
{
    @Id
    @SequenceGenerator(name = "wordform_sequence", sequenceName = "wordform_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordform_sequence")
    private Long id;

    @Column(name = "wordform")
    private String word;

    private String lemma;

    private String pos;

    @Column(name = "gram")
    private String grammar;

    private String translation;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                    CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "sentences_wordforms",
            joinColumns = @JoinColumn(name = "wordform_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "sentence_id", nullable = false)
    )
    private Set<Sentence> sentences;

    @Enumerated
    @Column(columnDefinition = "int")
    private LanguageType lang;

    public WordDto toDto()
    {
        Map<String, String> gram;
        if (grammar.isEmpty())
        {
            gram = Map.of();
        }
        else
        {
            gram = Arrays.stream(grammar.split("\\|"))
                    .map(s -> s.split("="))
                    .collect(Collectors.toMap(e -> e[0], e -> e[1], (e1, e2) -> e1));
        }
        return new WordDto(word, lemma, pos, gram);
    }
}
