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

    private final String queryTemplate = """           
                select distinct x1.si id, x1.os original_sentence, x1.t translation, x1.lang lang from x x1
                """;

    private final String joinTemplate = """
                join x x%d
                on x%d.o + 1 = x%d.o and x%d.i + 1 = x%d.i
                """;

    private final String lemmaTemplate = "x%d.lemma = ?%d";

    private final String posTemplate = "x%d.pos = ?%d";

    private final String grammarColumnTemplate = "x%d.gram";

    private final String grammarDelimiter = " || '\\_' || ";

    private final String grammarTemplate = "%s like ?%d ";

    private final String pagingTemplate = " limit %d offset %d";

    @Override
    public List<Sentence> findByMultipleComplexQuery(List<ComplexQueryRequest> request, int page, int maxResults)
    {
        var query = new StringBuilder(queryTemplate);
        var conditions = new ArrayList<String[]>(request.size() * 3);
        var grammarColumns = new ArrayList<String>(request.size());
        var grammarSearchStrings = new ArrayList<String>(request.size());

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
                grammarColumns.add(grammarColumnTemplate.formatted(i + 1));
                grammarSearchStrings.add("%" + request.get(i).getStingifiedGrammar() + "%");
            }
        }
        
        query.append("where ");
        query.append(conditions.stream().map(a -> a[1]).collect(Collectors.joining(" and ")));
        if (!grammarColumns.isEmpty())
        {
            if (!conditions.isEmpty())
            {
                query.append(" and ");
            }
            var grammarColumnsString = String.join(grammarDelimiter, grammarColumns);
            query.append(grammarTemplate.formatted(grammarColumnsString, conditions.size()));
        }
        query.append(pagingTemplate.formatted(maxResults, page * maxResults));

        var nativeQuery = entityManager.createNativeQuery(query.toString(), Sentence.class);
        for (int i = 0; i < conditions.size(); ++i)
        {
            nativeQuery.setParameter(i, conditions.get(i)[0]);
        }
        if (!grammarColumns.isEmpty())
        {
            var grammarSearchString = String.join("\\_", grammarSearchStrings);
            nativeQuery.setParameter(conditions.size(), grammarSearchString);
        }
        return nativeQuery.getResultList();
    }
}
