package com.gosha.kalosha.hauzijan.repository;

import com.gosha.kalosha.hauzijan.model.dto.ComplexQueryRequest;
import com.gosha.kalosha.hauzijan.model.entity.Sentence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SentenceRepositoryCustom
{
    List<Sentence> findByMultipleComplexQuery(List<ComplexQueryRequest> multipleComplexQuery, int page, int maxResults);
}
