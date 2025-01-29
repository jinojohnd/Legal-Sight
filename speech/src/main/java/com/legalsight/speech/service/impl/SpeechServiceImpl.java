package com.legalsight.speech.service.impl;

import com.legalsight.speech.dto.SpeechDTO;
import com.legalsight.speech.jpa.SpeechJPA;
import com.legalsight.speech.mapper.SpeechMapper;
import com.legalsight.speech.repository.SpeechRepository;
import com.legalsight.speech.repository.specification.SearchCriteria;
import com.legalsight.speech.repository.specification.SearchOperation;
import com.legalsight.speech.repository.specification.SpeechJPASpec;
import com.legalsight.speech.repository.specification.SpeechSpecification;
import com.legalsight.speech.service.SpeechService;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpeechServiceImpl implements SpeechService {

  @Autowired
  SpeechRepository repository;

  @Autowired
  SpeechMapper speechMapper;

  @Override
  public List<SpeechDTO> searchSpeeches(String author, Date startDate, Date endDate,
      String textSnippet, List<String> keywords) {

    SpeechJPASpec spec = new SpeechJPASpec();
    spec.addCriteria(new SearchCriteria("keywords.keyword", keywords, SearchOperation.IN));
    spec.addCriteria(new SearchCriteria("author", author, SearchOperation.LIKE));
    spec.addCriteria(new SearchCriteria("content", textSnippet, SearchOperation.LIKE));

    List<SpeechJPA> speeches = repository.findAll(spec);
    return speechMapper.speechJPAListToSpeechDTOList(speeches);
  }

  @Override
  public SpeechDTO getSpeech(UUID speechId) {
    SpeechJPASpec spec = new SpeechJPASpec();
    spec.addCriteria(new SearchCriteria("id", speechId, SearchOperation.EQUAL));
    Optional<SpeechJPA> jpa = repository.findOne(spec);
    if (jpa.isPresent()) {
      return speechMapper.toSpeechDTO(jpa.get());
    }
    log.warn("Speech Id {} not found", speechId);
    return new SpeechDTO();
  }

  @Override
  @Transactional
  public UUID saveSpeech(SpeechDTO speechDTO) {
    SpeechJPA speechJPA = speechMapper.toSpeechJPA(speechDTO);

    if (null != speechJPA.getKeywords()) {
      speechJPA.getKeywords().forEach(keyword -> keyword.setSpeech(speechJPA));
    }

    SpeechJPA savedSpeech = repository.save(speechJPA);
    return savedSpeech.getId();
  }
}
