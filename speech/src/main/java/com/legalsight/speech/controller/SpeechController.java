package com.legalsight.speech.controller;

import com.legalsight.speech.dto.SpeechDTO;
import com.legalsight.speech.model.GenericResponse;
import com.legalsight.speech.service.SpeechService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping(value = "/get-speech/{speechId}")
  public ResponseEntity<SpeechDTO> getSpeech(@PathVariable UUID speechId) {

    SpeechDTO speech = speechService.getSpeech(speechId);
    return ResponseEntity.ok(speech);
  }

  @PostMapping(value = "/save-speech")
  public ResponseEntity<GenericResponse> saveSpeech(@RequestBody SpeechDTO speechDTO) {

    UUID speechId = speechService.saveSpeech(speechDTO);
    GenericResponse response = new GenericResponse(0, "Speech created successfully " + speechId);
    return ResponseEntity.ok(response);
  }

  @PutMapping(value = "/edit-speech")
  public ResponseEntity<GenericResponse> editSpeech(@RequestBody SpeechDTO speechDTO) {

    try {
      speechService.editSpeech(speechDTO);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.internalServerError().body(new GenericResponse(1, "Speech not found "));
    }
    GenericResponse response = new GenericResponse(0, "Speech updated successfully ");
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(value = "/delete-speech/{speechId}")
  public ResponseEntity<GenericResponse> deleteSpeech(@PathVariable UUID speechId) {

    try {
      speechService.deleteSpeech(speechId);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.internalServerError().body(new GenericResponse(1, "Speech not found "));
    }
    GenericResponse response = new GenericResponse(0, "Speech deleted successfully ");
    return ResponseEntity.ok(response);
  }

}
