package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.model.dto.ComplexQueryRequest;
import com.gosha.kalosha.hauzijan.model.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.model.dto.WordDto;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import com.gosha.kalosha.hauzijan.service.WordService;
import com.gosha.kalosha.hauzijan.util.QueryDecoder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.rest.endpoint}" + "query")
@CrossOrigin({"${api.frontend.development}", "${api.frontend.production}"})
public class QueryController
{
    private final WordService wordService;

    private final SentenceService sentenceService;

    private final QueryDecoder decoder;

    @Autowired
    public QueryController(WordService wordService, SentenceService sentenceService, QueryDecoder decoder)
    {
        this.wordService = wordService;
        this.sentenceService = sentenceService;
        this.decoder = decoder;
    }

    /**
     * @param query query string
     * @param page page's numeration starts from 1, default value 1
     * @param maxResults maximum number of results per page, default value 20
     * @return list of sentences if any is found
     */
    @GetMapping("simple")
    public List<SentenceDto> makeSimpleQuery(@RequestParam String query,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "20", name = "max_results") int maxResults)
    {
        return sentenceService.getBySimpleQuery(query, page - 1, maxResults);
    }

    /**
     * @param encoded base64 encoded query map
     * @param page page's numeration starts from 1, default value 1
     * @param maxResults maximum number of results per page, default value 20
     * @return list of sentences if any is found
     */
    @SneakyThrows
    @GetMapping("complex")
    public List<SentenceDto> makeComplexQuery(@RequestParam String encoded,
                                              @RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "20", name = "max_results") int maxResults)
    {
        var query = (Map<String, Object>) decoder.decodeJsonToObject(encoded, Map.class);
        return sentenceService.getByParameters(query, page - 1, maxResults);
    }

    @SneakyThrows
    @GetMapping("multiple")
    public List<SentenceDto> makeMultipleComplexQuery(@RequestParam List<String> encoded,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "20", name = "max_results") int maxResults)
    {
        var query = encoded.stream()
                .map(s -> decoder.decodeJsonToObject(s, ComplexQueryRequest.class))
                .toList();
        return sentenceService.getMultipleByParameters(query, page - 1, maxResults);
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
