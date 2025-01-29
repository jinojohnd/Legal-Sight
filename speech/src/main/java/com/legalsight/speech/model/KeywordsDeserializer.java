package com.legalsight.speech.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.legalsight.speech.dto.KeywordsDTO;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeywordsDeserializer extends JsonDeserializer<List<KeywordsDTO>> {

  @Override
  public List<KeywordsDTO> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
    return Stream.of(jsonParser.getText().split(","))
        .map(keyword -> new KeywordsDTO(keyword.trim()))
        .collect(Collectors.toList());
  }
}
