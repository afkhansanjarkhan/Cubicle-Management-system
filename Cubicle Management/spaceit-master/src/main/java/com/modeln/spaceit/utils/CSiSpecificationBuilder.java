package com.modeln.spaceit.utils;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSiSpecificationBuilder<T> {
    private final List<SearchCriteria> params;

    public CSiSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public CSiSpecificationBuilder with(String key, String operation, String value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public CSiSpecification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        List<CSiSpecification<T>> specs = params.stream()
                .map(CSiSpecification<T> :: new)
                .collect(Collectors.toList());

        Specification<T> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {

            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return (CSiSpecification<T> )result;
    }
}
