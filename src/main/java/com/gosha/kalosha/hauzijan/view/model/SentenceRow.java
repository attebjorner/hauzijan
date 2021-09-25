package com.gosha.kalosha.hauzijan.view.model;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SentenceRow
{
    private long id;

    private String sentence;

    public static SentenceRow fromDto(SentenceDto sentenceDto)
    {
        return new SentenceRow(sentenceDto.id(), sentenceDto.originalSentence());
    }
}
