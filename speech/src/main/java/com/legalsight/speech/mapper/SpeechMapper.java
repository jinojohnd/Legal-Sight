package com.legalsight.speech.mapper;

import com.legalsight.speech.dto.KeywordsDTO;
import com.legalsight.speech.dto.SpeechDTO;
import com.legalsight.speech.jpa.KeywordsJPA;
import com.legalsight.speech.jpa.SpeechJPA;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpeechMapper {

  SpeechDTO toSpeechDTO(SpeechJPA speechJPA);
  KeywordsDTO toKeywordsDTO(KeywordsJPA keywordsJPA);

  List<SpeechDTO> speechJPAListToSpeechDTOList(List<SpeechJPA> speechJPAs);

  SpeechJPA toSpeechJPA(SpeechDTO speechDTO);
  KeywordsJPA toKeywordsJPA(KeywordsDTO keywordsDTO);
}
