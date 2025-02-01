package com.legalsight.speech.service;

import com.legalsight.speech.dto.SpeechDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SpeechService {

  List<SpeechDTO> searchSpeeches(String author, LocalDate startDate, LocalDate endDate,
      String content, List<String> keywords);

  SpeechDTO getSpeech(UUID speechId);

  UUID saveSpeech(SpeechDTO speechDTO);

  void editSpeech(SpeechDTO speechDTO) throws IllegalArgumentException;

  void deleteSpeech(UUID id) throws IllegalArgumentException;
}
