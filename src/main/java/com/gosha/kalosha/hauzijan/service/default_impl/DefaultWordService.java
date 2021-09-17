package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.model.Word;
import com.gosha.kalosha.hauzijan.repository.WordRepository;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return wordRepository.getById(id).toDto();
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

    @Override
    @Transactional
    public List<WordDto> getWordlist(long id)
    {
        List<Word> wordList = wordRepository.getWordListBySentenceId(id);
        if (wordList.isEmpty())
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        return wordList.stream().map(Word::toDto).toList();
    }
}
