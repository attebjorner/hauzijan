package com.gosha.kalosha.hauzijan.repository;

import com.gosha.kalosha.hauzijan.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>
{
    @Query("select w from Sentence s join s.wordList w where s.id = ?1")
    List<Word> getWordListBySentenceId(long id);
}
