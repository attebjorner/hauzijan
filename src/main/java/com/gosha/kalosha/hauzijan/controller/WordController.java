package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.model.Word;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.rest.endpoint}" + "word")
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

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateWord(@PathVariable long id, @RequestBody Word word)
    {
        wordService.update(id, word);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWord(@PathVariable long id)
    {
        wordService.delete(id);
    }
}
