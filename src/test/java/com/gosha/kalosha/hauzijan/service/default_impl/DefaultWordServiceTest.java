package com.gosha.kalosha.hauzijan.service.default_impl;

import com.gosha.kalosha.hauzijan.model.dto.WordDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.model.enums.LanguageType;
import com.gosha.kalosha.hauzijan.model.entity.Word;
import com.gosha.kalosha.hauzijan.model.mapper.WordMapper;
import com.gosha.kalosha.hauzijan.repository.WordRepository;
import com.gosha.kalosha.hauzijan.service.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultWordServiceTest
{
    @Mock
    private WordRepository wordRepository;

    private WordService underTest;

    @BeforeEach
    void setUp()
    {
        underTest = new DefaultWordService(wordRepository);
    }

    @Test
    void getWordlist()
    {
        // given
        var id = 6293L;
        var word = new Word(
                "بلدان", "بَلَد", "NOUN", "Number=Plur",
                "countries", new HashSet<>(), LanguageType.ARA
        );
        given(wordRepository.getWordListBySentenceId(anyLong()))
                .willReturn(List.of(word));
        // when
        List<WordDto> expected = underTest.getWordlist(id);
        // then
        verify(wordRepository).getWordListBySentenceId(id);
        assertThat(expected).isEqualTo(List.of(WordMapper.toDto(word)));
    }

    @Test
    void willThrowIfSentenceNotFound()
    {
        // given
        var id = 6293L;
        // when
        // then
        assertThatThrownBy(() -> underTest.getWordlist(id))
                .isInstanceOf(NoSentencesFoundException.class);
        verify(wordRepository).getWordListBySentenceId(id);
    }
}