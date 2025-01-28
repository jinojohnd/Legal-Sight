package com.legalsight.speech.repository;

import com.legalsight.speech.jpa.SpeechJPA;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeechRepository extends JpaRepository<SpeechJPA, UUID>,
    JpaSpecificationExecutor<SpeechJPA> {

}
