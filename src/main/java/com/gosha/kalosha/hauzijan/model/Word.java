package com.gosha.kalosha.hauzijan.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "wordform_gram",
            joinColumns = {@JoinColumn(name = "wordform_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "gram_feature")
    @Column(name = "gram_value")
    private Map<String, String> gram;

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
}
