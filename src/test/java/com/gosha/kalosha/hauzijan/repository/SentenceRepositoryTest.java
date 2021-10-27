package com.gosha.kalosha.hauzijan.repository;

import com.gosha.kalosha.hauzijan.model.dto.ComplexQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SentenceRepositoryTest
{
    @Autowired
    private SentenceRepository underTest;

    @Test
    void shouldFoo()
    {
        var x = List.of(
                new ComplexQueryRequest(null, "NOUN", null, null),
                new ComplexQueryRequest(null, "VERB", null, null)
        );
        var expected = underTest.findByMultipleComplexQuery(x, 0, 10);
        assertThat(expected.size()).isEqualTo(10);
    }
}