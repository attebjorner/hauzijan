package com.gosha.kalosha.hauzijan.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gosha.kalosha.hauzijan.model.entity.Sentence;

public record SentenceDto(
        long id,
        @JsonProperty("original_sentence") String originalSentence
)
{
}