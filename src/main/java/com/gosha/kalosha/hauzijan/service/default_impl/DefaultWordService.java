package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.model.dto.WordDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.exception_handing.NoWordsFoundException;
import com.gosha.kalosha.hauzijan.model.entity.Word;
import com.gosha.kalosha.hauzijan.model.mapper.WordMapper;
import com.gosha.kalosha.hauzijan.repository.WordRepository;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        Optional<Word> word = wordRepository.findById(id);
        if (word.isEmpty())
        {
            throw new NoWordsFoundException("Word with id " + id + " does not exist");
        }
        return WordMapper.toDto(word.get());
    }

    @Override
    public long save(Word word)
    {
        return wordRepository.save(word).getId();
    }

    @Override
    public void update(long id, Word word)
    {
        Optional<Word> underUpdate = wordRepository.findById(id);
        if (underUpdate.isEmpty())
        {
            throw new NoWordsFoundException("Word with id " + id + " does not exist");
        }
        underUpdate.get().copyNonNullProperties(word);
        wordRepository.save(underUpdate.get());
    }

    @Override
    public void delete(long id)
    {
        Optional<Word> word = wordRepository.findById(id);
        if (word.isEmpty())
        {
            throw new NoWordsFoundException("Word with id " + id + " does not exist");
        }
        if (word.get().getSentences().isEmpty())
        {
            throw new IllegalStateException("Word with id " + id + " is used in some sentences, thus it cannot be deleted");
        }
        wordRepository.delete(word.get());
    }

    @Override
    public List<WordDto> getWordlist(long id)
    {
        List<Word> wordList = wordRepository.getWordListBySentenceId(id);
        if (wordList.isEmpty())
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        return wordList.stream().map(WordMapper::toDto).toList();
    }
}
