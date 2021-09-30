package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.model.LanguageType;
import com.gosha.kalosha.hauzijan.model.Sentence;
import com.gosha.kalosha.hauzijan.repository.SentenceRepository;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static com.gosha.kalosha.hauzijan.model.ParameterType.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultSentenceServiceTest
{
    @Mock
    private SentenceRepository sentenceRepository;

    private SentenceService underTest;

    private final Map<String, Object> byParametersQuery = Map.of(
            LEMMA, "כלב",
            POS, "NOUN",
            GRAM, Map.of("Number", "Plur")
    );

    private final String byGramString = "Number=Plur";

    private final Sentence sentence = new Sentence(
            6293L, List.of(), "test sentence", "test translation", LanguageType.ARA
    );

    @BeforeEach
    void setUp()
    {
        underTest = new DefaultSentenceService(sentenceRepository);
        ReflectionTestUtils.invokeMethod(underTest, DefaultSentenceService.class, "init");
    }

    @Test
    void canGetBySimpleQueryIfPageAndMaxResultsAreNull()
    {
        // given
        var query = "מה";
        var pageable = PageRequest.of(0, 20, Sort.by("id"));
        given(sentenceRepository.findDistinctByOriginalSentenceContaining(anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getBySimpleQuery(query, null, null);
        // then
        verify(sentenceRepository).findDistinctByOriginalSentenceContaining(query, pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetBySimpleQueryIfPageIsNull()
    {
        // given
        var query = "מה";
        var pageable = PageRequest.of(0, 30, Sort.by("id"));
        given(sentenceRepository.findDistinctByOriginalSentenceContaining(anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getBySimpleQuery(query, null, 30);
        // then
        verify(sentenceRepository).findDistinctByOriginalSentenceContaining(query, pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetBySimpleQueryIfMaxResultsIsNull()
    {
        // given
        var query = "מה";
        var pageable = PageRequest.of(1, 20, Sort.by("id"));
        given(sentenceRepository.findDistinctByOriginalSentenceContaining(anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getBySimpleQuery(query, 2, null);
        // then
        verify(sentenceRepository).findDistinctByOriginalSentenceContaining(query, pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetBySimpleQueryIfPageAndMaxResultsNotNull()
    {
        // given
        var query = "מה";
        var pageable = PageRequest.of(1, 30, Sort.by("id"));
        given(sentenceRepository.findDistinctByOriginalSentenceContaining(anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getBySimpleQuery(query, 2, 30);
        // then
        verify(sentenceRepository).findDistinctByOriginalSentenceContaining(query, pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void willThrowIfSentenceNotFoundBySimpleQuery()
    {
        // given
        var query = "מה";
        var pageable = PageRequest.of(1, 30, Sort.by("id"));
        // when
        // then
        assertThatThrownBy(() -> underTest.getBySimpleQuery(query, 2, 30))
                .isInstanceOf(NoSentencesFoundException.class);
        verify(sentenceRepository).findDistinctByOriginalSentenceContaining(query, pageable);
    }

    @Test
    void canGetByParametersByLemma()
    {
        // given
        Map<String, Object> query = Map.of(LEMMA, byParametersQuery.get(LEMMA));
        var pageable = PageRequest.of(0, 20);
        given(sentenceRepository.findAllByLemma(anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getByParameters(query, null, null);
        // then
        verify(sentenceRepository).findAllByLemma((String) query.get(LEMMA), pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetByParametersByPos()
    {
        // given
        Map<String, Object> query = Map.of(POS, byParametersQuery.get(POS));
        var pageable = PageRequest.of(0, 20);
        given(sentenceRepository.findAllByPos(anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getByParameters(query, null, null);
        // then
        verify(sentenceRepository).findAllByPos((String) query.get(POS), pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetByParametersByGram()
    {
        // given
        Map<String, Object> query = Map.of(GRAM, byParametersQuery.get(GRAM));
        var pageable = PageRequest.of(0, 20);
        given(sentenceRepository.findAllByGram(anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getByParameters(query, null, null);
        // then
        verify(sentenceRepository).findAllByGram(byGramString, pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetByParametersByLemmaPos()
    {
        // given
        Map<String, Object> query = Map.of(
                LEMMA, byParametersQuery.get(LEMMA),
                POS, byParametersQuery.get(POS)
        );
        var pageable = PageRequest.of(0, 20);
        given(sentenceRepository.findAllByLemmaPos(anyString(), anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getByParameters(query, null, null);
        // then
        verify(sentenceRepository).findAllByLemmaPos((String) query.get(LEMMA), (String) query.get(POS), pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetByParametersByLemmaGram()
    {
        // given
        Map<String, Object> query = Map.of(
                LEMMA, byParametersQuery.get(LEMMA),
                GRAM, byParametersQuery.get(GRAM)
        );
        var pageable = PageRequest.of(0, 20);
        given(sentenceRepository.findAllByLemmaGram(anyString(), anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getByParameters(query, null, null);
        // then
        verify(sentenceRepository).findAllByLemmaGram((String) query.get(LEMMA), byGramString, pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetByParametersByPosGram()
    {
        // given
        Map<String, Object> query = Map.of(
                POS, byParametersQuery.get(POS),
                GRAM, byParametersQuery.get(GRAM)
        );
        var pageable = PageRequest.of(0, 20);
        given(sentenceRepository.findAllByPosGram(anyString(), anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected =  underTest.getByParameters(query, null, null);
        // then
        verify(sentenceRepository).findAllByPosGram((String) query.get(POS), byGramString, pageable);
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void canGetByParametersByLemmaPosGram()
    {
        // given
        Map<String, Object> query = Map.of(
                LEMMA, byParametersQuery.get(LEMMA),
                POS, byParametersQuery.get(POS),
                GRAM, byParametersQuery.get(GRAM)
        );
        var pageable = PageRequest.of(0, 20);
        given(sentenceRepository.findAllByLemmaPosGram(anyString(), anyString(), anyString(), any(PageRequest.class)))
                .willReturn(List.of(sentence));
        // when
        List<SentenceDto> expected = underTest.getByParameters(query, null, null);
        // then
        verify(sentenceRepository).findAllByLemmaPosGram(
                (String) query.get(LEMMA), (String) query.get(POS),
                byGramString, pageable
        );
        assertThat(expected).isEqualTo(List.of(SentenceDto.fromSentence(sentence)));
    }

    @Test
    void willThrowWhenWrongExtraQueryParameters()
    {
        // given
        Map<String, Object> query = Map.of(
                LEMMA, byParametersQuery.get(LEMMA),
                "extra", "value"
        );
        // when
        // then
        assertThatThrownBy(() -> underTest.getByParameters(query, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Wrong query parameters");
        verify(sentenceRepository, never()).findAllByLemma(anyString(), any(PageRequest.class));
        verify(sentenceRepository, never()).findAllByLemmaGram(anyString(), anyString(), any(PageRequest.class));
    }

    @Test
    void willThrowWhenGrammarIsNotMap()
    {
        // given
        Map<String, Object> query = Map.of(
                LEMMA, byParametersQuery.get(LEMMA),
                GRAM, "value"
        );
        // when
        // then
        assertThatThrownBy(() -> underTest.getByParameters(query, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Grammar should be presented as a key-value structure");
        verify(sentenceRepository, never()).findAllByLemmaGram(anyString(), anyString(), any(PageRequest.class));
    }

    @Test
    void willThrowIfSentenceNotFoundByParameters()
    {
        // given
        Map<String, Object> query = Map.of(
                LEMMA, byParametersQuery.get(LEMMA)
        );
        var pageable = PageRequest.of(1, 30);
        // when
        // then
        assertThatThrownBy(() -> underTest.getByParameters(query, 2, 30))
                .isInstanceOf(NoSentencesFoundException.class);
        verify(sentenceRepository).findAllByLemma((String) query.get(LEMMA), pageable);
    }
}