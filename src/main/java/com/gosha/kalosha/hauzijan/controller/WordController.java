package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.model.Word;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.gosha.kalosha.hauzijan.util.Util.decodeJsonToObject;

@RestController
@RequestMapping("word")
public class WordController
{
    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService)
    {
        this.wordService = wordService;
    }

    @GetMapping("{id}")
    public WordDto getById(@PathVariable long id)
    {
        return wordService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> saveWord(@RequestBody Word word)
    {
        return new ResponseEntity<>(Map.of("id", wordService.save(word)), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateWord(@RequestBody Word word)
    {
        wordService.update(word);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteWord(@RequestBody Word word)
    {
        wordService.delete(word);
    }

    /**
     * @param id sentence's id
     * @return list of words in the order they appear in the sentence
     */
    @GetMapping("wordlist/{id}")
    public List<WordDto> getWordlist(@PathVariable long id)
    {
        return wordService.getWordlist(id);
    }
}
