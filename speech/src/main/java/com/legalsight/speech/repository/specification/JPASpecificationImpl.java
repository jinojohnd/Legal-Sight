package com.legalsight.speech.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class JPASpecificationImpl<T> implements Specification<T> {

  @Serial
  private static final long serialVersionUID = 1L;

  private final List<SearchCriteria> criteriaList;

  public JPASpecificationImpl() {
    this.criteriaList = new ArrayList<>();
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    List<Predicate> predicates = new ArrayList<>();

    for (SearchCriteria criteria : criteriaList) {
      Path<Object> path = getPath(root, criteria.getKey());
      Object value = criteria.getValue();
      if (null == value) {
        continue;
      }
      switch (criteria.getOperation()) {
        case GREATER_THAN:
          predicates.add(criteriaBuilder.greaterThan(path.as(String.class), value.toString()));
          break;
        case LESS_THAN:
          predicates.add(criteriaBuilder.lessThan(path.as(String.class), value.toString()));
          break;
        case GREATER_THAN_EQUAL:
          addDateOrStringPredicate(criteriaBuilder, path, value, predicates, true);
          break;
        case LESS_THAN_EQUAL:
          addDateOrStringPredicate(criteriaBuilder, path, value, predicates, false);
          break;
        case EQUAL:
          predicates.add(criteriaBuilder.equal(path, value));
          break;
        case LIKE:
          predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)),
              "%" + value.toString().toLowerCase() + "%"));
          break;
        case IN:
          if (value instanceof Collection) {
            predicates.add(path.in((Collection<?>) value));
          } else {
            throw new IllegalArgumentException("Value for IN operation must be a collection");
          }
          break;
        default:
          throw new UnsupportedOperationException(
              "Operation not supported: " + criteria.getOperation());
      }
    }

    query.distinct(true);
    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }

  private void addDateOrStringPredicate(CriteriaBuilder criteriaBuilder, Path<Object> path,
      Object value, List<Predicate> predicates, boolean greaterThanOrEqual) {
    if (value instanceof Date) {
      if (greaterThanOrEqual) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(path.as(Date.class), (Date) value));
      } else {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(path.as(Date.class), (Date) value));
      }
    } else {
      if (greaterThanOrEqual) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), value.toString()));
      } else {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(path.as(String.class), value.toString()));
      }
    }
  }

  private Path<Object> getPath(Root<T> root, String key) {
    String[] keyParts = key.split("\\.");
    Path<Object> path = (Path<Object>) root;
    Join<?, ?> join = null;

    for (int i = 0; i < keyParts.length - 1; i++) {
      join = (join == null) ? root.join(keyParts[i]) : join.join(keyParts[i]);
      path = (Path<Object>) join;
    }

    return path.get(keyParts[keyParts.length - 1]);
  }

  public void addCriteria(SearchCriteria criteria) {
    this.criteriaList.add(criteria);
  }
}