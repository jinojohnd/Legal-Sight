package com.legalsight.speech.service;

import com.legalsight.speech.dto.SpeechDTO;
import java.util.Date;
import java.util.List;

public interface SpeechService {

  List<SpeechDTO> searchSpeeches(String author, Date startDate, Date endDate,
      String textSnippet, List<String> keywords);
}
