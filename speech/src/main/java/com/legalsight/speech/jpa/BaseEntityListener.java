package com.legalsight.speech.jpa;

import jakarta.persistence.PrePersist;
import java.util.UUID;

public class BaseEntityListener {

  @PrePersist
  public void prePersist(BaseJPA entity) {
    if (entity.getId() == null) {
      entity.setId(UUID.randomUUID());
    }
  }
}
