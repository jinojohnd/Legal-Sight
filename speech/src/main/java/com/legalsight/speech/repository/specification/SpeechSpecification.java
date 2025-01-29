package com.legalsight.speech.repository.specification;

import com.legalsight.speech.jpa.SpeechJPA;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class SpeechSpecification {

  public static Specification<SpeechJPA> searchSpeeches(String author, Date startDate, Date endDate,
      String textSnippet, List<String> keywords) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      Path<Object> path = getPath(root, "keywords.keyword");
      predicates.add(path.in((Collection<?>) keywords));

      path = getPath(root, "author");
      predicates.add(criteriaBuilder.equal(path, author));

      assert query != null;
      query.distinct(true);
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  private static Path<Object> getPath(Root<?> root, String key) {
    String[] keyParts = key.split("\\.");
    Path<Object> path = (Path<Object>) root;
    Join<?, ?> join = null;

    for (int i = 0; i < keyParts.length - 1; i++) {
      join = (join == null) ? root.join(keyParts[i]) : join.join(keyParts[i]);
      path = (Path<Object>) join;
    }

    return path.get(keyParts[keyParts.length - 1]);
  }


}
