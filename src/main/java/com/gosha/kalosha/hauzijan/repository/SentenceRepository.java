package com.gosha.kalosha.hauzijan.repository;

import com.gosha.kalosha.hauzijan.model.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long>
{
    List<Sentence> findAllByOriginalSentenceContaining(String query);

    @Query("select distinct s from Word w join w.sentences s where w.lemma = ?1")
    List<Sentence> findAllByLemma(String lemma);

    @Query("select distinct s from Word w join w.sentences s where w.pos = ?1")
    List<Sentence> findAllByPos(String pos);

    @Query("select distinct s from Word w join w.sentences s where w.grammar like ?1")
    List<Sentence> findAllByGram(String grammar);

    @Query("select distinct s from Word w join w.sentences s where w.lemma = ?1 and w.pos = ?2")
    List<Sentence> findAllByLemmaPos(String lemma, String pos);

    @Query("select distinct s from Word w join w.sentences s where w.lemma = ?1 and w.grammar like ?2")
    List<Sentence> findAllByLemmaGram(String lemma, String grammar);

    @Query("select distinct s from Word w join w.sentences s where w.pos = ?1 and w.grammar like ?2")
    List<Sentence> findAllByPosGram(String pos, String grammar);

    @Query("select distinct s from Word w join w.sentences s where w.lemma = ?1 and w.pos = ?2 and w.grammar like ?3")
    List<Sentence> findAllByLemmaPosGram(String lemma, String pos, String grammar);
}
