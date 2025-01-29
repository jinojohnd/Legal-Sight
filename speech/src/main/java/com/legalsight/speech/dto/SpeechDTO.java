package com.legalsight.speech.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.legalsight.speech.model.KeywordsDeserializer;
import java.util.Date;
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
  private Date speechDate;
  private String content;

  @JsonDeserialize(using = KeywordsDeserializer.class)
  private List<KeywordsDTO> keywords;
}
