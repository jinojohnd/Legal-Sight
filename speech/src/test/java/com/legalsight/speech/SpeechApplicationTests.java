package com.legalsight.speech;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legalsight.speech.dto.KeywordsDTO;
import com.legalsight.speech.dto.SpeechDTO;
import com.legalsight.speech.model.GenericResponse;
import com.legalsight.speech.service.SpeechService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SpeechApplicationTests {

  private static UUID savedSpeechId;

  private boolean speechEdited = false;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SpeechService speechService;

  @BeforeAll
  public void setup() throws Exception {
    SpeechDTO speechDTO = new SpeechDTO();
    speechDTO.setAuthor("John Doe");
    speechDTO.setSpeechDate(LocalDate.of(2024, 5, 20));
    speechDTO.setContent("This is a test speech.");
    speechDTO.setKeywords(List.of(new KeywordsDTO("test"), new KeywordsDTO("speech")));

    MvcResult result = mockMvc.perform(post("/speech/save-speech")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(speechDTO)))
        .andExpect(status().isOk())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    GenericResponse response = objectMapper.readValue(responseJson, GenericResponse.class);
    savedSpeechId = UUID.fromString(response.getMessage().toString().split(" ")[3]);

		log.info("Speech: {}, created successfully for test", savedSpeechId);
  }

	@AfterAll
  public void cleanUp() throws Exception {
		if (null == savedSpeechId) {
			return;
		}

    MvcResult result = mockMvc.perform(delete("/speech/delete-speech/" + savedSpeechId))
        .andExpect(status().isOk())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    GenericResponse response = objectMapper.readValue(responseJson, GenericResponse.class);

    assertThat(response.getStatus()).isEqualTo(0);
    assertThat(response.getMessage().toString()).contains("Speech deleted successfully");

		log.info("Speech: {}, deleted successfully after the test", savedSpeechId);
  }

  @Test
  @Order(1)
  public void testEditSpeech() throws Exception {
    SpeechDTO speechDTO = new SpeechDTO();
    speechDTO.setAuthor("Jane Doe");
    speechDTO.setSpeechDate(LocalDate.of(2025, 1, 25));
    speechDTO.setContent("This is a test speech by Jane Doe.");
    speechDTO.setId(savedSpeechId);
    speechDTO.setKeywords(List.of(new KeywordsDTO("test jane"), new KeywordsDTO("speech jane")));

    MvcResult result = mockMvc.perform(put("/speech/edit-speech")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(speechDTO)))
        .andExpect(status().isOk())
        .andReturn();
		speechEdited = true;

    String responseJson = result.getResponse().getContentAsString();
    GenericResponse response = objectMapper.readValue(responseJson, GenericResponse.class);

    assertThat(response.getStatus()).isEqualTo(0);
    assertThat(response.getMessage().toString()).contains("Speech updated successfully");

  }

  @Test
  @Order(2)
  public void testGetSpeech() throws Exception {
    MvcResult result = mockMvc.perform(get("/speech/get-speech/" + savedSpeechId))
        .andExpect(status().isOk())
        .andReturn();

    String responseJson = result.getResponse().getContentAsString();
    SpeechDTO retrievedSpeech = objectMapper.readValue(responseJson, SpeechDTO.class);

    if (speechEdited) {
      // Assert the updated author (Jane Doe) if the speech has been edited
      assertThat(retrievedSpeech.getAuthor()).isEqualTo("Jane Doe");
      assertThat(retrievedSpeech.getSpeechDate()).isEqualTo(LocalDate.of(2025, 1, 25));
      assertThat(retrievedSpeech.getContent()).isEqualTo("This is a test speech by Jane Doe.");
    } else {
      // Assert the original author (John Doe) if the speech has not been edited
      assertThat(retrievedSpeech.getAuthor()).isEqualTo("John Doe");
      assertThat(retrievedSpeech.getSpeechDate()).isEqualTo(LocalDate.of(2024, 5, 20));
      assertThat(retrievedSpeech.getContent()).isEqualTo("This is a test speech.");
    }
  }
}