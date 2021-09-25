package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.model.Sentence;
import com.gosha.kalosha.hauzijan.repository.SentenceRepository;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.gosha.kalosha.hauzijan.model.ParameterType.*;
import static com.gosha.kalosha.hauzijan.model.ParameterType.POS;

@Service
@Transactional
public class DefaultSentenceService implements SentenceService
{
    private final SentenceRepository sentenceRepository;

    private static final Map<Set<String>, Function<Map<String, Object>, List<Sentence>>> COMPLEX_QUERY_METHODS = new HashMap<>();

    @Autowired
    public DefaultSentenceService(SentenceRepository sentenceRepository)
    {
        this.sentenceRepository = sentenceRepository;
    }

    @Override
    public SentenceDto getById(long id)
    {
        Optional<Sentence> sentence = sentenceRepository.findById(id);
        if (sentence.isEmpty())
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        return sentence.get().toDto();
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
    public List<SentenceDto> getBySimpleQuery(String queryString, Integer page, Integer maxResults)
    {
        List<Sentence> sentences = sentenceRepository.findDistinctByOriginalSentenceContaining(
                queryString, PageRequest.of(
                        page == null ? 0 : page,
                        maxResults == null ? 20 : maxResults,
                        Sort.by("id")
                )
        );
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream().map(Sentence::toDto).toList();
    }

    @Override
    public List<SentenceDto> getBySimpleQuery(String queryString)
    {
        return getBySimpleQuery(queryString, null, null);
    }

    @Override
    public List<SentenceDto> getBySimpleQuery(String[] queryStrings, Integer page, Integer maxResults)
    {
        String queryString = String.join("%", queryStrings);
        return getBySimpleQuery(queryString, page, maxResults);
    }

    @Override
    public List<SentenceDto> getByParameters(Map<String, Object> query)
    {
        return getByParameters(query, null, null);
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
                page == null ? 0 : page, maxResults == null ? 20 : maxResults
        );
        query.put(PAGE, pageProperties);
        if (query.containsKey(GRAM))
        {
            if (!(query.get(GRAM) instanceof Map))
            {
                throw new IllegalArgumentException("Grammar should be presented as a key-value structure");
            }
            String grammar = ((Map<String, String>) query.get(GRAM)).entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining("%"));
            query.put(GRAM, grammar);
        }
        List<Sentence> sentences = queryMethod.apply(query);
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream().map(Sentence::toDto).toList();
    }

    @PostConstruct
    private void fillComplexQueryMethodsMap()
    {
        COMPLEX_QUERY_METHODS.putAll(Map.of(
                Set.of(LEMMA), x -> sentenceRepository.findAllByLemma((String) x.get(LEMMA), (Pageable) x.get(PAGE)),
                Set.of(POS), x -> sentenceRepository.findAllByPos((String) x.get(POS), (Pageable) x.get(PAGE)),
                Set.of(GRAM), x -> sentenceRepository.findAllByGram((String) x.get(GRAM), (Pageable) x.get(PAGE)),
                Set.of(LEMMA, POS), x -> sentenceRepository.findAllByLemmaPos(
                        (String) x.get(LEMMA), (String) x.get(POS), (Pageable) x.get(PAGE)
                ),
                Set.of(LEMMA, GRAM), x -> sentenceRepository.findAllByLemmaGram(
                        (String) x.get(LEMMA), (String) x.get(GRAM), (Pageable) x.get(PAGE)
                ),
                Set.of(POS, GRAM), x -> sentenceRepository.findAllByPosGram(
                        (String) x.get(POS), (String) x.get(GRAM), (Pageable) x.get(PAGE)
                ),
                Set.of(LEMMA, POS, GRAM), x -> sentenceRepository.findAllByLemmaPosGram(
                        (String) x.get(LEMMA), (String) x.get(POS), (String) x.get(GRAM), (Pageable) x.get(PAGE)
                )
        ));
    }
}
