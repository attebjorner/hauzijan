package com.gosha.kalosha.hauzijan.service;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.model.Word;

public interface WordService
{
    WordDto getById(long id);

    long save(Word word);

    void update(Word word);

    void delete(Word word);
}
