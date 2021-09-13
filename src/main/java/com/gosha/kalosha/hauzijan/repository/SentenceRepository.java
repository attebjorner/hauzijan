package com.gosha.kalosha.hauzijan.repository;

import com.gosha.kalosha.hauzijan.model.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long>
{
}
