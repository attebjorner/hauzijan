package com.gosha.kalosha.hauzijan.model.mapper;

import com.gosha.kalosha.hauzijan.model.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.model.entity.Sentence;

public class SentenceMapper
{
    public static SentenceDto toDto(Sentence sentence)
    {
        return new SentenceDto(sentence.getId(), sentence.getOriginalSentence());
    }
}
