package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.model.Sentence;
import com.gosha.kalosha.hauzijan.repository.SentenceRepository;
import com.gosha.kalosha.hauzijan.service.SentenceQueryFunction;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static com.gosha.kalosha.hauzijan.model.ParameterType.*;

@Service
@Transactional
public class DefaultSentenceService implements SentenceService
{
    private final SentenceRepository sentenceRepository;

    private final Map<Set<String>, SentenceQueryFunction> COMPLEX_QUERY_METHODS = new HashMap<>();

    @Autowired
    public DefaultSentenceService(SentenceRepository sentenceRepository)
    {
        this.sentenceRepository = sentenceRepository;
    }

    @PostConstruct
    private void init()
    {
        COMPLEX_QUERY_METHODS.putAll(Map.of(
                Set.of(LEMMA), (q, page) -> sentenceRepository.findAllByLemma(q.get(LEMMA), page),
                Set.of(POS), (q, page) -> sentenceRepository.findAllByPos(q.get(POS), page),
                Set.of(GRAM), (q, page) -> sentenceRepository.findAllByGram(q.get(GRAM), page),
                Set.of(LEMMA, POS), (q, page) -> sentenceRepository.findAllByLemmaPos(q.get(LEMMA), q.get(POS), page),
                Set.of(LEMMA, GRAM), (q, page) -> sentenceRepository.findAllByLemmaGram(q.get(LEMMA), q.get(GRAM), page),
                Set.of(POS, GRAM), (q, page) -> sentenceRepository.findAllByPosGram(q.get(POS), q.get(GRAM), page),
                Set.of(LEMMA, POS, GRAM), (q, page) -> sentenceRepository.findAllByLemmaPosGram(
                        q.get(LEMMA), q.get(POS), q.get(GRAM), page
                )
        ));
    }

    @Override
    public SentenceDto getById(long id)
    {
        Optional<Sentence> sentence = sentenceRepository.findById(id);
        if (sentence.isEmpty())
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        return SentenceDto.fromSentence(sentence.get());
    }

    @Override
    public void delete(long id)
    {
        Optional<Sentence> sentence = sentenceRepository.findById(id);
        if (sentence.isEmpty())
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        sentenceRepository.delete(sentence.get());
    }

    @Override
    public void update(long id, Sentence sentence)
    {
        Optional<Sentence> underUpdate = sentenceRepository.findById(id);
        if (underUpdate.isEmpty())
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        underUpdate.get().copyNonNullProperties(sentence);
        sentenceRepository.save(underUpdate.get());
    }

    @Override
    public long save(Sentence sentence)
    {
        return sentenceRepository.save(sentence).getId();
    }

    @Override
    public List<SentenceDto> getBySimpleQuery(String queryString, int page)
    {
        return getBySimpleQuery(queryString, page, null);
    }

    @Override
    public List<SentenceDto> getBySimpleQuery(String queryString, Integer page, Integer maxResults)
    {
        List<Sentence> sentences = sentenceRepository.findDistinctByOriginalSentenceContaining(
                queryString, PageRequest.of(
                        page == null ? 0 : page - 1,
                        maxResults == null ? 20 : maxResults,
                        Sort.by("id")
                )
        );
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream().map(SentenceDto::fromSentence).toList();
    }

    @Override
    public List<SentenceDto> getByParameters(Map<String, Object> query, int page)
    {
        return getByParameters(query, page, null);
    }

    @Override
    public List<SentenceDto> getByParameters(Map<String, Object> query, Integer page, Integer maxResults)
    {
        var queryMethod = COMPLEX_QUERY_METHODS.get(query.keySet());
        if (queryMethod == null)
        {
            throw new IllegalArgumentException("Wrong query parameters");
        }
        var pageProperties = PageRequest.of(
                page == null ? 0 : page - 1, maxResults == null ? 20 : maxResults
        );
        var fullQuery = new HashMap<String, String>();
        fullQuery.put(LEMMA, (String) query.getOrDefault(LEMMA, ""));
        fullQuery.put(POS, (String) query.getOrDefault(POS, ""));
        if (query.containsKey(GRAM))
        {
            if (!(query.get(GRAM) instanceof Map))
            {
                throw new IllegalArgumentException("Grammar should be presented as a key-value structure");
            }
            String grammar = ((Map<String, String>) query.get(GRAM)).entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .sorted()
                    .collect(Collectors.joining("%"));
            fullQuery.put(GRAM, grammar);
        }
        List<Sentence> sentences = queryMethod.find(fullQuery, pageProperties);
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream().map(SentenceDto::fromSentence).toList();
    }
}
