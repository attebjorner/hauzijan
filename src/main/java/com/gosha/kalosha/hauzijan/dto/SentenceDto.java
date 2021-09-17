package com.gosha.kalosha.hauzijan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SentenceDto(
        long id,
        @JsonProperty("original_sentence") String originalSentence
)
{
}
