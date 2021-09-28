package com.gosha.kalosha.hauzijan.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class QueryControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void canGetSimpleQuery() throws Exception
    {
        var query = "מה";
        mockMvc.perform(get("/api/v2/query/simple").param("query", query))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].original_sentence").isString());
    }

    @Test
    public void willReturn400WhenSimpleQueryNotFoundSentences() throws Exception
    {
        var query = "abc";
        mockMvc.perform(get("/api/v2/query/simple").param("query", query))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("No sentences found"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "{\"lemma\":\"כלב\"}",
            "{\"pos\":\"NOUN\"}",
            "{\"gram\":{\"Number\":\"Plur\"}}",
            "{\"lemma\":\"כלב\",\"pos\":\"NOUN\"}",
            "{\"lemma\":\"כלב\",\"gram\":{\"Number\":\"Plur\"}}",
            "{\"pos\":\"NOUN\",\"gram\":{\"Number\":\"Plur\"}}",
            "{\"lemma\":\"כלב\",\"pos\":\"NOUN\",\"gram\":{\"Number\":\"Plur\"}}"
    }, delimiter = ' ')
    public void canGetComplexQuery(String jsonRequest) throws Exception
    {
        var encoded = new String(Base64.getEncoder().encode(jsonRequest.getBytes(StandardCharsets.UTF_8)));
        mockMvc.perform(get("/api/v2/query/complex").param("encoded", encoded))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].original_sentence").isString());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "{\"pos\":22}",
            "{\"gram\":{\"Number\":3}}",
            "{\"lemma\":\"כלב\",\"pos\":4}",
            "{\"lemma\":5,\"gram\":{\"Number\":\"Plur\"}}",
            "{\"pos\":\"NOUN\",\"gram\":{\"Number\":66}}",
            "{\"lemma\":\"כלב\",\"pos\":35,\"gram\":{\"Number\":22}}"
    }, delimiter = ' ')
    public void willReturn400ValuesOfWrongType(String jsonRequest) throws Exception
    {
        var encoded = new String(Base64.getEncoder().encode(jsonRequest.getBytes(StandardCharsets.UTF_8)));
        mockMvc.perform(get("/api/v2/query/complex").param("encoded", encoded))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("All values should be of type string"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "{\"extra\":\"value\"}",
            "{\"pos\":\"NOUN\",\"extra\":\"value\"}",
            "{\"lemma\":\"כלב\",\"pos\":\"NOUN\",\"extra\":\"value\"}",
            "{\"lemma\":\"כלב\",\"gram\":{\"Number\":\"Plur\"},\"extra\":\"value\"}"
    }, delimiter = ' ')
    public void willReturn400ExtraParameters(String jsonRequest) throws Exception
    {
        var encoded = new String(Base64.getEncoder().encode(jsonRequest.getBytes(StandardCharsets.UTF_8)));
        mockMvc.perform(get("/api/v2/query/complex").param("encoded", encoded))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Wrong query parameters"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "{\"gram\":\"value\"}",
            "{\"lemma\":\"כלב\",\"pos\":\"NOUN\",\"gram\":\"value\"}",
            "{\"lemma\":\"כלב\",\"gram\":\"value\"}"
    }, delimiter = ' ')
    public void willReturn400GrammarIsNotMap(String jsonRequest) throws Exception
    {
        var encoded = new String(Base64.getEncoder().encode(jsonRequest.getBytes(StandardCharsets.UTF_8)));
        mockMvc.perform(get("/api/v2/query/complex").param("encoded", encoded))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Grammar should be presented as a key-value structure"));
    }

    @Test
    public void willReturn400WhenComplexQueryNotFoundSentences() throws Exception
    {
        var query = "{\"lemma\":\"abc\"}";
        var encoded = new String(Base64.getEncoder().encode(query.getBytes(StandardCharsets.UTF_8)));
        mockMvc.perform(get("/api/v2/query/complex").param("encoded", encoded))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("No sentences found"));
    }

    @Test
    public void canGetWordlist() throws Exception
    {
        mockMvc.perform(get("/api/v2/query/wordlist/6293"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].word").isString())
                .andExpect(jsonPath("$[0].lemma").isString())
                .andExpect(jsonPath("$[0].pos").isString())
                .andExpect(jsonPath("$[0].grammar").isMap());
    }

    @Test
    public void willReturn400WhenSentenceWithIdNotFound() throws Exception
    {
        var id = 1L;
        mockMvc.perform(get("/api/v2/query/wordlist/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Sentence with id " + id + " does not exist"));
    }
}
