package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.model.Sentence;
import com.gosha.kalosha.hauzijan.repository.SentenceRepository;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultSentenceService implements SentenceService
{
    private final SentenceRepository sentenceRepository;

    @Autowired
    public DefaultSentenceService(SentenceRepository sentenceRepository)
    {
        this.sentenceRepository = sentenceRepository;
    }

    @Override
    public SentenceDto getById(long id)
    {
        return SentenceDto.fromSentence(sentenceRepository.getById(id));
    }

    @Override
    public void delete(Sentence sentence)
    {
        sentenceRepository.delete(sentence);
    }

    @Override
    public void update(Sentence sentence)
    {
        sentenceRepository.save(sentence);
    }

    @Override
    public long save(Sentence sentence)
    {
        return sentenceRepository.save(sentence).getId();
    }
}
