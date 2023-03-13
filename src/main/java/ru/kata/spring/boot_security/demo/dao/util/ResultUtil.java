package ru.kata.spring.boot_security.demo.dao.util;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ResultUtil {
    public static <T> Optional<T> getSingleResultOrNull(TypedQuery<T> var) {
        try {
            return Optional.of(var.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> getSingleResultOrNull(Query var) {
        try {
            return Optional.of((T) var.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> Set<T> getSetResultOrNull(TypedQuery<T> var) {
        try {
            return new HashSet<T>(var.getResultList());
        } catch (Exception e) {
            return Set.of();
        }
    }
    public static <T> Set<T> getSetResultOrNull(Query var) {
        try {
            return new HashSet<T>((Collection<? extends T>) var.getResultList());
        } catch (Exception e) {
            return Set.of();
        }
    }
}
