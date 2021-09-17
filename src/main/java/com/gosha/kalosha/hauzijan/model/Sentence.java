package com.gosha.kalosha.hauzijan.model;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sentences")
@Data
public class Sentence
{
    @Id
    @SequenceGenerator(name = "sentence_sequence", sequenceName = "sentence_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sentence_sequence")
    private Long id;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                    CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "sentences_wordforms",
            joinColumns = @JoinColumn(name = "sentence_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "wordform_id", nullable = false)
    )
    @OrderColumn(name = "wordlist_order")
    private List<Word> wordList;

    @Column(name = "original_sentence")
    private String originalSentence;

    private String translation;

    @Enumerated
    @Column(columnDefinition = "int")
    private LanguageType lang;

    public SentenceDto toDto()
    {
        return new SentenceDto(id, originalSentence);
    }
}
