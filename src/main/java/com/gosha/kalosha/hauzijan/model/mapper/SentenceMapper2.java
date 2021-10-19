package com.gosha.kalosha.hauzijan.model.mapper;

import com.gosha.kalosha.hauzijan.model.dto.SentenceDto2;
import com.gosha.kalosha.hauzijan.model.entity.Sentence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SentenceMapper2
{
    @Mapping(target = "id", source = "sentence.id")
    @Mapping(target = "originalSentence", source = "sentence.originalSentence")
    SentenceDto2 toDto(Sentence sentence);
}
