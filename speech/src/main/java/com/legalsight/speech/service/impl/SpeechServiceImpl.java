package com.legalsight.speech.service.impl;

import com.legalsight.speech.dto.SpeechDTO;
import com.legalsight.speech.jpa.SpeechJPA;
import com.legalsight.speech.mapper.SpeechMapper;
import com.legalsight.speech.repository.KeywordsRepository;
import com.legalsight.speech.repository.SpeechRepository;
import com.legalsight.speech.repository.specification.SearchCriteria;
import com.legalsight.speech.repository.specification.SearchOperation;
import com.legalsight.speech.repository.specification.SpeechJPASpec;
import com.legalsight.speech.service.SpeechService;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpeechServiceImpl implements SpeechService {

  private final SpeechRepository speechRepository;
  private final KeywordsRepository keywordsRepository;
  private final SpeechMapper speechMapper;

  @Override
  public List<SpeechDTO> searchSpeeches(String author, Date startDate, Date endDate,
      String content, List<String> keywords) {

    SpeechJPASpec spec = new SpeechJPASpec();
    if (keywords != null && !keywords.isEmpty()) {
      spec.addCriteria(new SearchCriteria("keywords.keyword", keywords, SearchOperation.IN));
    }
    if (author != null && !author.isBlank()) {
      spec.addCriteria(new SearchCriteria("author", author, SearchOperation.LIKE));
    }
    if (content != null && !content.isBlank()) {
      spec.addCriteria(new SearchCriteria("content", content, SearchOperation.LIKE));
    }

    List<SpeechJPA> speeches = speechRepository.findAll(spec);
    return speechMapper.speechJPAListToSpeechDTOList(speeches);
  }

  @Override
  public SpeechDTO getSpeech(UUID speechId) {
    return speechRepository.findById(speechId)
        .map(speechMapper::toSpeechDTO)
        .orElseGet(() -> {
          log.warn("Speech ID {} not found", speechId);
          return new SpeechDTO();
        });
  }

  @Override
  @Transactional
  public UUID saveSpeech(SpeechDTO speechDTO) {
    SpeechJPA speechJPA = speechMapper.toSpeechJPA(speechDTO);

    if (speechJPA.getKeywords() != null) {
      speechJPA.getKeywords().forEach(keyword -> keyword.setSpeech(speechJPA));
    }

    SpeechJPA savedSpeech = speechRepository.save(speechJPA);
    return savedSpeech.getId();
  }

  @Override
  @Transactional
  public void editSpeech(SpeechDTO speechDTO) {
    if (!speechRepository.existsById(speechDTO.getId())) {
      throw new IllegalArgumentException("Speech not found");
    }

    keywordsRepository.deleteBySpeechId(speechDTO.getId());
    saveSpeech(speechDTO);
  }

  @Override
  @Transactional
  public void deleteSpeech(UUID id) {
    if (!speechRepository.existsById(id)) {
      throw new IllegalArgumentException("Speech not found");
    }
    speechRepository.deleteById(id);
  }
}
