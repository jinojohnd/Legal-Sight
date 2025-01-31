package com.legalsight.speech.repository;

import com.legalsight.speech.jpa.KeywordsJPA;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordsRepository extends JpaRepository<KeywordsJPA, UUID>,
    JpaSpecificationExecutor<KeywordsJPA> {

  @Modifying
  @Query("DELETE FROM keywords k WHERE k.speech.id = :speechId")
  void deleteBySpeechId(@Param("speechId") UUID speechId);
}
