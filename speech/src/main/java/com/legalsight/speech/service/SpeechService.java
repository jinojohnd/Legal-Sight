package com.legalsight.speech.service;

import com.legalsight.speech.jpa.SpeechJPA;
import java.util.Date;
import java.util.List;

public interface SpeechService {

  List<SpeechJPA> searchSpeeches(String author, Date startDate, Date endDate,
      String textSnippet, List<String> keywords);
}
