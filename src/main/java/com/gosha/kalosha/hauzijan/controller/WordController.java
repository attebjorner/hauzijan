package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.model.Word;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.gosha.kalosha.hauzijan.util.Util.decodeJsonToObject;

@RestController
@RequestMapping("${api_version}" + "/word")
public class WordController
{
    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService)
    {
        this.wordService = wordService;
    }

    @RequestMapping("{id}")
    public WordDto getById(@PathVariable long id)
    {
        return wordService.getById(id);
    }

    @RequestMapping("save")
    public ResponseEntity<Map<String, Long>> saveWord(@RequestParam(name = "word") String encodedWord)
    {
        Word word = decodeJsonToObject(encodedWord, Word.class);
        return new ResponseEntity<>(Map.of("id", wordService.save(word)), HttpStatus.CREATED);
    }

    @RequestMapping("update")
    public void updateWord(@RequestParam(name = "word") String encodedWord)
    {

    }

    @RequestMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWord(@RequestParam(name = "word") String encodedWord)
    {
        Word word = decodeJsonToObject(encodedWord, Word.class);
        wordService.delete(word);
    }
}
