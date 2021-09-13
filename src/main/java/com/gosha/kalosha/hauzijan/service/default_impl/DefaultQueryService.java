package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.repository.SentenceRepository;
import com.gosha.kalosha.hauzijan.service.QueryService;
import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.model.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultQueryService implements QueryService
{
    private final SentenceRepository sentenceRepository;

    private static final Map<Set<String>, Function<Object[], List<Sentence>>> COMPLEX_QUERY_METHODS = new HashMap<>();

    @Autowired
    public DefaultQueryService(SentenceRepository sentenceRepository)
    {
        this.sentenceRepository = sentenceRepository;
    }

    @Override
    @Transactional
    public List<SentenceDto> getByParameters(Map<String, Object> query, Integer page, Integer maxResults)
    {
//        List<Object> values = new ArrayList<>(query.values());
//        values.add((page == null) ? 0 : page - 1);
//        values.add((maxResults == null) ? 10 : maxResults);
//        var queryFunction = COMPLEX_QUERY_METHODS.get(query.keySet());
//        if (queryFunction == null)
//        {
//            throw new IllegalStateException("Wrong query parameters");
//        }
//        List<Sentence> sentences = queryFunction.apply(values.toArray());
//        if (sentences.isEmpty())
//        {
//            throw new NoSentencesFoundException("No sentences found");
//        }
//        return sentences.stream().map(SentenceDto::fromSentence).collect(Collectors.toList());
        return null;
    }

    @Override
    @Transactional
    public List<SentenceDto> getBySimpleQuery(String queryString, Integer page, Integer maxResults)
    {
//        List<Sentence> sentences = sentenceDao.getByQuery(
//                queryString, (page == null) ? 0 : page - 1, (maxResults == null) ? 10 : maxResults
//        );
//        if (sentences.isEmpty())
//        {
//            throw new NoSentencesFoundException("No sentences found");
//        }
//        return sentences.stream().map(SentenceDto::fromSentence).collect(Collectors.toList());
        return null;
    }

    @Override
    @Transactional
    public List<WordDto> getWordlist(long id)
    {
//        Sentence s = sentenceDao.getById(id);
//        if (s == null)
//        {
//            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
//        }
//        return s.getWordList().stream().map(WordDto::fromWord).collect(Collectors.toList());
        return null;
    }

    @PostConstruct
    private void fillComplexQueryMethodsMap()
    {
//        COMPLEX_QUERY_METHODS.putAll(Map.of(
//                Set.of("lemma"), x -> sentenceDao.getByLemma((String) x[0], (Integer) x[1], (Integer) x[2]),
//                Set.of("pos"), x -> sentenceDao.getByPos((String) x[0], (Integer) x[1], (Integer) x[2]),
//                Set.of("gram"), x -> sentenceDao.getByGram((Map<String, String>) x[0], (Integer) x[1], (Integer) x[2]),
//                Set.of("lemma", "pos"), x -> sentenceDao.getByLemmaPos(
//                        (String) x[0], (String) x[1], (Integer) x[2], (Integer) x[3]
//                ),
//                Set.of("lemma", "gram"), x -> sentenceDao.getByLemmaGram(
//                        (String) x[1], (Map<String, String>) x[0], (Integer) x[2], (Integer) x[3]
//                ),
//                Set.of("pos", "gram"), x -> sentenceDao.getByPosGram(
//                        (String) x[1], (Map<String, String>) x[0], (Integer) x[2], (Integer) x[3]
//                ),
//                Set.of("lemma", "pos", "gram"), x -> sentenceDao.getByLemmaPosGram(
//                        (String) x[1], (String) x[2], (Map<String, String>) x[0], (Integer) x[3], (Integer) x[4]
//                )
//        ));
    }
}
