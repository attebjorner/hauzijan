package com.gosha.kalosha.hauzijan.service;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.model.Sentence;

public interface SentenceService
{
    SentenceDto getById(long id);

    long save(Sentence sentence);

    void update(Sentence sentence);

    void delete(Sentence sentence);
}
