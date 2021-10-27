package com.gosha.kalosha.hauzijan.repository;

import com.gosha.kalosha.hauzijan.model.dto.ComplexQueryRequest;
import com.gosha.kalosha.hauzijan.model.entity.Sentence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SentenceRepositoryCustomImpl implements SentenceRepositoryCustom
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Sentence> findByMultipleComplexQuery(List<ComplexQueryRequest> request, int page, int maxResults)
    {
        var query = new StringBuilder("""
                with x as\s
                (
                	select sx.id si, sx.original_sentence os, sx.translation t, sx.lang lang,
                 	sw.id i, wordlist_order o, lemma, pos, gram
                	from sentences_wordforms sw
                	inner join wordforms wx
                	on sw.wordform_id = wx.id
                	inner join sentences sx
                	on sx.id = sw.sentence_id
                )
                                
                select distinct x1.si id, x1.os original_sentence, x1.t translation, x1.lang lang from x x1
                """);
        var joinTemplate = """
                join x x%d
                on x%d.o + 1 = x%d.o and x%d.i + 1 = x%d.i
                """;
        var lemmaTemplate = "x%d.lemma = ?%d";
        var posTemplate = "x%d.pos = ?%d";
        var grammarTemplate = "x%d.gram like ?%d";
        var pagingTemplate = " limit %d offset %d";
        var conditions = new ArrayList<String[]>(request.size() * 3);

        for (int i = 0; i < request.size(); ++i)
        {
            if (i != 0) query.append(joinTemplate.formatted(i + 1, i, i + 1, i, i + 1));
            if (request.get(i).getLemma() != null)
            {
                conditions.add(new String[]{request.get(i).getLemma(), lemmaTemplate.formatted(i + 1, conditions.size())});
            }
            if (request.get(i).getPos() != null)
            {
                conditions.add(new String[]{request.get(i).getPos(), posTemplate.formatted(i + 1, conditions.size())});
            }
            if (request.get(i).getStingifiedGrammar() != null)
            {
                conditions.add(new String[]{request.get(i).getStingifiedGrammar(), grammarTemplate.formatted(i + 1, conditions.size())});
            }
        }
        query.append("where ");
        query.append(conditions.stream().map(a -> a[1]).collect(Collectors.joining(" and ")));
        query.append(pagingTemplate.formatted(maxResults, page * maxResults));
        var nativeQuery = entityManager.createNativeQuery(query.toString(), Sentence.class);
        for (int i = 0; i < conditions.size(); ++i)
        {
            nativeQuery.setParameter(i, conditions.get(i)[0]);
        }
        return nativeQuery.getResultList();
    }
}
