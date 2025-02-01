package com.legalsight.speech.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeechDTO extends BaseDTO {

  private String author;
  private LocalDate speechDate;
  private String content;
  private List<KeywordsDTO> keywords;
}
