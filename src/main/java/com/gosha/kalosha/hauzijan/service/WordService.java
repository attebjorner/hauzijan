package com.gosha.kalosha.hauzijan.service;

import com.gosha.kalosha.hauzijan.model.dto.WordDto;
import com.gosha.kalosha.hauzijan.model.entity.Word;

import java.util.List;

public interface WordService
{
    WordDto getById(long id);

    long save(Word word);

    void update(long id, Word word);

    void delete(long id);

    List<WordDto> getWordlist(long id);
}
