package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.model.Sentence;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.rest.endpoint}" + "sentence")
public class SentenceController
{
    private final SentenceService sentenceService;

    @Autowired
    public SentenceController(SentenceService sentenceService)
    {
        this.sentenceService = sentenceService;
    }

    @GetMapping("{id}")
    public SentenceDto getById(@PathVariable long id)
    {
        return sentenceService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> saveWord(@RequestBody Sentence sentence)
    {
        return new ResponseEntity<>(Map.of("id", sentenceService.save(sentence)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateWord(@PathVariable long id, @RequestBody Sentence sentence)
    {
        sentenceService.update(id, sentence);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWord(@PathVariable long id)
    {
        sentenceService.delete(id);
    }
}
