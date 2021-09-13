package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.model.Word;
import com.gosha.kalosha.hauzijan.repository.WordRepository;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultWordService implements WordService
{
    private final WordRepository wordRepository;

    @Autowired
    public DefaultWordService(WordRepository wordRepository)
    {
        this.wordRepository = wordRepository;
    }

    @Override
    public WordDto getById(long id)
    {
        return WordDto.fromWord(wordRepository.getById(id));
    }

    @Override
    public long save(Word word)
    {
        return wordRepository.save(word).getId();
    }

    @Override
    public void update(Word word)
    {
        wordRepository.save(word);
    }

    @Override
    public void delete(Word word)
    {
        wordRepository.delete(word);
    }
}
