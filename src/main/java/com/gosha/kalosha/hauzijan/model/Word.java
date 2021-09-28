package com.gosha.kalosha.hauzijan.model;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "wordforms")
@Data @NoArgsConstructor
public class Word implements NonNullPropertiesCopyable
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

    public Word(String word, String lemma, String pos, String grammar,
                String translation, Set<Sentence> sentences, LanguageType lang)
    {
        this.word = word;
        this.lemma = lemma;
        this.pos = pos;
        this.grammar = grammar;
        this.translation = translation;
        this.sentences = sentences;
        this.lang = lang;
    }
}
