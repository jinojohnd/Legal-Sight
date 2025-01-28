package com.legalsight.speech.service.impl;

import com.legalsight.speech.jpa.SpeechJPA;
import com.legalsight.speech.repository.SpeechRepository;
import com.legalsight.speech.repository.specification.SpeechSpecification;
import com.legalsight.speech.service.SpeechService;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpeechServiceImpl implements SpeechService {

  @Autowired
  SpeechRepository repository;

  @Override
  public List<SpeechJPA> searchSpeeches(String author, Date startDate, Date endDate,
      String textSnippet, List<String> keywords) {
    var specification = SpeechSpecification.searchSpeeches(author, startDate, endDate, textSnippet,
        keywords);
    return repository.findAll(specification);
  }
}
