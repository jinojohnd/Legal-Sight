package com.legalsight.speech.repository.specification;

import com.legalsight.speech.jpa.SpeechJPA;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class SpeechSpecification {

  public static Specification<SpeechJPA> searchSpeeches(String author, Date startDate, Date endDate,
      String textSnippet, List<String> keywords) {
    return (root, query, criteriaBuilder) -> {
      var predicates = criteriaBuilder.conjunction();

      // Search by author
      if (author != null && !author.isEmpty()) {
        predicates.getExpressions().add(
            criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                "%" + author.toLowerCase() + "%")
        );
      }

      // Filter by date range
      if (startDate != null) {
        predicates.getExpressions().add(
            criteriaBuilder.greaterThanOrEqualTo(root.get("speechDate"), startDate)
        );
      }
      if (endDate != null) {
        predicates.getExpressions().add(
            criteriaBuilder.lessThanOrEqualTo(root.get("speechDate"), endDate)
        );
      }

      // Search by text snippet in content
      if (textSnippet != null && !textSnippet.isEmpty()) {
        predicates.getExpressions().add(
            criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
                "%" + textSnippet.toLowerCase() + "%")
        );
      }

      // Filter by keywords
      if (keywords != null && !keywords.isEmpty()) {
        var keywordsJoin = root.join("keywords");
        predicates.getExpressions().add(
            keywordsJoin.get("keyword").in(keywords)
        );
      }
      return predicates;
    };

  }
}
