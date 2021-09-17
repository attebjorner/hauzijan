package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.dto.WordDto;
import com.gosha.kalosha.hauzijan.service.QueryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.gosha.kalosha.hauzijan.util.Util.decodeJsonToObject;

@RestController
@RequestMapping("query")
public class QueryController
{
    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService)
    {
        this.queryService = queryService;
    }

    /**
     * @param query query string
     * @param page page's numeration starts from 1, default value 1
     * @param maxResults maximum number of results per page, default value 10
     * @return list of sentences if any is found
     */
    @GetMapping("simple")
    public List<SentenceDto> makeSimpleQuery(@RequestParam String query,
                                             @RequestParam(required = false) Integer page,
                                             @RequestParam(required = false, name = "max_results") Integer maxResults)
    {
        return queryService.getBySimpleQuery(query, page, maxResults);
    }

    /**
     * @param encoded base64 encoded query map
     * @param page page's numeration starts from 1, default value 1
     * @param maxResults maximum number of results per page, default value 10
     * @return list of sentences if any is found
     */
    @SneakyThrows
    @GetMapping("complex")
    public List<SentenceDto> makeComplexQuery(@RequestParam String encoded,
                                              @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false, name = "max_results") Integer maxResults)
    {
        Map<String, Object> query = (Map<String, Object>) decodeJsonToObject(encoded, Map.class);
        return queryService.getByParameters(query, page, maxResults);
    }

    /**
     * @param id sentence's id
     * @return list of words in the order they appear in the sentence
     */
    @GetMapping("wordlist/{id}")
    public List<WordDto> getWordlist(@PathVariable long id)
    {
        return queryService.getWordlist(id);
    }
}
