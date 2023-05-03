package com.gosha.kalosha.hauzijan.model.entity;

import com.gosha.kalosha.hauzijan.model.enums.LanguageType;
import com.gosha.kalosha.hauzijan.model.NonNullPropertiesCopyable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sentences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sentence implements NonNullPropertiesCopyable
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

    public Sentence(List<Word> wordList, String originalSentence, String translation, LanguageType lang)
    {
        this.wordList = wordList;
        this.originalSentence = originalSentence;
        this.translation = translation;
        this.lang = lang;
    }
}
