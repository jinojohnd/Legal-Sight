package com.legalsight.speech.controller;

import com.legalsight.speech.dto.SpeechDTO;
import com.legalsight.speech.service.SpeechService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/speech")
@Slf4j
public class SpeechController {

  @Autowired
  SpeechService speechService;

  @GetMapping(value = "/get-speeches")
  public ResponseEntity<List<SpeechDTO>> getSpeeches(@RequestParam(required = false) String author,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
      @RequestParam(required = false) String textSnippet,
      @RequestParam(required = false) List<String> keywords) {

    List<SpeechDTO> speechDTOS = speechService.searchSpeeches(author, startDate, endDate,
        textSnippet, keywords);
    return ResponseEntity.ok(speechDTOS);
  }
}
