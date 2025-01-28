package com.legalsight.speech.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "keywords")
@Table(name = "KEYWORDS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeywordsJPA extends BaseJPA {

  @NotNull
  @Column(name = "KEYWORD", columnDefinition = "VARCHAR(255)")
  private String keyword;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SPEECH_ID", columnDefinition = "UUID")
  private SpeechJPA speech;
}
