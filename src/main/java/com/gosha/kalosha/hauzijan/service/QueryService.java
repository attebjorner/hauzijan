package com.gosha.kalosha.hauzijan.service;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.dto.WordDto;

import java.util.List;
import java.util.Map;

public interface QueryService
{
    List<SentenceDto> getByParameters(Map<String, Object> query, Integer page, Integer maxResults);

    List<SentenceDto> getBySimpleQuery(String queryString, Integer page, Integer maxResults);

    List<WordDto> getWordlist(long id);
}
