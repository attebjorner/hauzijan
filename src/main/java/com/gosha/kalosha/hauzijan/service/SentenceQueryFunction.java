package com.gosha.kalosha.hauzijan.service;

import com.gosha.kalosha.hauzijan.model.entity.Sentence;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface SentenceQueryFunction
{
    List<Sentence> find(Map<String, String> query, Pageable page);
}
