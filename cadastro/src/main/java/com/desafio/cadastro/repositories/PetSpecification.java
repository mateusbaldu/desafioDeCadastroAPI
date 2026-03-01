package com.desafio.cadastro.repositories;

import com.desafio.cadastro.models.Pets;
import com.desafio.cadastro.models.dtos.pets.PetSearchFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.Normalizer;
import java.util.Optional;
import java.util.stream.Stream;

public class PetSpecification {

    private PetSpecification() {}

    public static Specification<Pets> build(PetSearchFilter filter) {
        return (root, query, cb) -> {
            Join<Object, Object> address = root.join("address", JoinType.LEFT);

            Predicate[] predicates = Stream.of(
                    Optional.of(cb.equal(root.get("petType"), filter.petType())),
                    Optional.ofNullable(filter.sex()).map(v -> cb.equal(root.get("petSex"), v)),
                    Optional.ofNullable(filter.age()).map(v -> cb.equal(root.get("age"), v)),
                    Optional.ofNullable(filter.weight()).map(v -> cb.equal(root.get("weight"), v)),
                    Optional.ofNullable(filter.name()).filter(s -> !s.isBlank()).map(v -> unaccentLike(root.get("name"), v, cb)),
                    Optional.ofNullable(filter.race()).filter(s -> !s.isBlank()).map(v -> unaccentLike(root.get("race"), v, cb)),
                    Optional.ofNullable(filter.city()).filter(s -> !s.isBlank()).map(v -> unaccentLike(address.get("city"), v, cb)),
                    Optional.ofNullable(filter.street()).filter(s -> !s.isBlank()).map(v -> unaccentLike(address.get("street"), v, cb))
            ).flatMap(Optional::stream).toArray(Predicate[]::new);

            return cb.and(predicates);
        };
    }

    private static Predicate unaccentLike(
            jakarta.persistence.criteria.Expression<String> field,
            String term,
            jakarta.persistence.criteria.CriteriaBuilder cb
    ) {
        String normalized = "%" + stripAccents(term.toLowerCase()) + "%";
        return cb.like(cb.function("unaccent", String.class, cb.lower(field)), normalized);
    }

    private static String stripAccents(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
