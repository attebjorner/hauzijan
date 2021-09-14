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
        var queryFunction = COMPLEX_QUERY_METHODS.get(query.keySet());
        if (queryFunction == null)
        {
            throw new IllegalStateException("Wrong query parameters");
        }
        if (query.containsKey("gram"))
        {
            String grammar = "%"
                    + ((Map<String, String>) query.get("gram")).entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining("%"))
                    + "%";
            query.put("gram", grammar);
        }
        List<Sentence> sentences = queryFunction.apply(query.values().toArray());
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream().map(SentenceDto::fromSentence).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SentenceDto> getBySimpleQuery(String queryString, Integer page, Integer maxResults)
    {
        List<Sentence> sentences = sentenceRepository.findAllByOriginalSentenceContaining(queryString);
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream().map(SentenceDto::fromSentence).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<WordDto> getWordlist(long id)
    {
        Optional<Sentence> s = sentenceRepository.findById(id);
        if (s.isEmpty())
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        return s.get().getWordList().stream().map(WordDto::fromWord).collect(Collectors.toList());
    }

    @PostConstruct
    private void fillComplexQueryMethodsMap()
    {
        COMPLEX_QUERY_METHODS.putAll(Map.of(
                Set.of("lemma"), x -> sentenceRepository.findAllByLemma((String) x[0]),
                Set.of("pos"), x -> sentenceRepository.findAllByPos((String) x[0]),
                Set.of("gram"), x -> sentenceRepository.findAllByGram((String) x[0]),
                Set.of("lemma", "pos"), x -> sentenceRepository.findAllByLemmaPos((String) x[0], (String) x[1]),
                Set.of("lemma", "gram"), x -> sentenceRepository.findAllByLemmaGram((String) x[1], (String) x[0]),
                Set.of("pos", "gram"), x -> sentenceRepository.findAllByPosGram((String) x[1], (String) x[0]),
                Set.of("lemma", "pos", "gram"), x -> sentenceRepository.findAllByLemmaPosGram(
                        (String) x[1], (String) x[2], (String) x[0]
                )
        ));
    }
}
