package com.gosha.kalosha.hauzijan.service;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.model.Sentence;

import java.util.List;
import java.util.Map;

public interface SentenceService
{
    SentenceDto getById(long id);

    long save(Sentence sentence);

    void update(long id, Sentence sentence);

    void delete(long id);

    List<SentenceDto> getBySimpleQuery(String queryString);

    List<SentenceDto> getBySimpleQuery(String queryString, Integer page, Integer maxResults);

    List<SentenceDto> getBySimpleQuery(String[] queryStrings, Integer page, Integer maxResults);

    List<SentenceDto> getByParameters(Map<String, Object> query);

    List<SentenceDto> getByParameters(Map<String, Object> query, Integer page, Integer maxResults);
}
