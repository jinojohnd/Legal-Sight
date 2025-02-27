package com.legalsight.speech.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Entity(name = "speech")
@Table(name = "SPEECH")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeechJPA extends BaseJPA {

  @NotNull
  @Column(name = "AUTHOR", columnDefinition = "VARCHAR(255)")
  private String author;

  @NotNull
  @Column(name = "SPEECH_DATE", columnDefinition = "DATE")
  private LocalDate speechDate;

  @NotNull
  @Column(name = "CONTENT", columnDefinition = "TEXT")
  private String content;

  @OneToMany(mappedBy = "speech", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<KeywordsJPA> keywords;

}
