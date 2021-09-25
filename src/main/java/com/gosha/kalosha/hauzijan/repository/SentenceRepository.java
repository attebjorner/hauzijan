package com.gosha.kalosha.hauzijan.repository;

import com.gosha.kalosha.hauzijan.model.Sentence;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long>
{
    List<Sentence> findDistinctByOriginalSentenceContaining(String query, Pageable pageable);

    @Query("select distinct s from Word w join w.sentences s where w.lemma = :lemma order by s.id")
    List<Sentence> findAllByLemma(@Param("lemma") String lemma, Pageable pageable);

    @Query("select distinct s from Word w join w.sentences s where w.pos = :pos order by s.id")
    List<Sentence> findAllByPos(@Param("pos") String pos, Pageable pageable);

    @Query("select distinct s from Word w join w.sentences s where w.grammar like concat('%', :grammar, '%') order by s.id")
    List<Sentence> findAllByGram(@Param("grammar") String grammar, Pageable pageable);

    @Query("select distinct s from Word w join w.sentences s where w.lemma = :lemma and w.pos = :pos order by s.id")
    List<Sentence> findAllByLemmaPos(@Param("lemma") String lemma, @Param("pos") String pos, Pageable pageable);

    @Query("""
           select distinct s from Word w
           join w.sentences s
           where w.lemma = :lemma and w.grammar like concat('%', :grammar, '%') order by s.id
           """
    )
    List<Sentence> findAllByLemmaGram(@Param("lemma") String lemma, @Param("grammar") String grammar, Pageable pageable);

    @Query("""
           select distinct s from Word w
           join w.sentences s
           where w.pos = :pos and w.grammar like concat('%', :grammar, '%') order by s.id
           """
    )
    List<Sentence> findAllByPosGram(@Param("pos") String pos, @Param("grammar") String grammar, Pageable pageable);

    @Query("""
           select distinct s from Word w
           join w.sentences s
           where w.lemma = :lemma and w.pos = :pos
               and w.grammar like concat('%', :grammar, '%') order by s.id
           """
    )
    List<Sentence> findAllByLemmaPosGram(@Param("lemma") String lemma, @Param("pos") String pos,
                                         @Param("grammar") String grammar, Pageable pageable);
}
