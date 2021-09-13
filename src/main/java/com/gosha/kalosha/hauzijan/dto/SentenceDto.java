package com.gosha.kalosha.hauzijan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gosha.kalosha.hauzijan.model.Sentence;
import lombok.Getter;

public class SentenceDto
{
    @Getter
    private final long id;

    @JsonProperty("original_sentence")
    @Getter
    private final String originalSentence;

    public SentenceDto(long id, String originalSentence)
    {
        this.id = id;
        this.originalSentence = originalSentence;
    }

    public static SentenceDto fromSentence(Sentence s)
    {
        return new SentenceDto(s.getId(), s.getOriginalSentence());
    }
}
