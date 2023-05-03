package com.gosha.kalosha.hauzijan;

import com.gosha.kalosha.hauzijan.config.SecurityConfig;
import com.gosha.kalosha.hauzijan.controller.QueryController;
import com.gosha.kalosha.hauzijan.controller.SentenceController;
import com.gosha.kalosha.hauzijan.controller.WordController;
import com.gosha.kalosha.hauzijan.repository.SentenceRepository;
import com.gosha.kalosha.hauzijan.repository.WordRepository;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
class HauzijanApplicationTests
{
    @Autowired
    private SentenceRepository sentenceRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private SentenceService sentenceService;

    @Autowired
    private WordService wordService;

    @Autowired
    private QueryController queryController;

    @Autowired
    private SentenceController sentenceController;

    @Autowired
    private WordController wordController;

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    void contextLoads()
    {
        assertThat(sentenceRepository, notNullValue());
        assertThat(wordRepository, notNullValue());
        assertThat(sentenceService, notNullValue());
        assertThat(wordService, notNullValue());
        assertThat(queryController, notNullValue());
        assertThat(sentenceController, notNullValue());
        assertThat(wordController, notNullValue());
        assertThat(securityConfig, notNullValue());
    }
}
