package com.gosha.kalosha.hauzijan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gosha.kalosha.hauzijan.model.Sentence;

public record SentenceDto(
        long id,
        @JsonProperty("original_sentence") String originalSentence
)
{
    public static SentenceDto fromSentence(Sentence sentence)
    {
        return new SentenceDto(sentence.getId(), sentence.getOriginalSentence());
    }
}
