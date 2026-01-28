package fr.univtln.bruno.demos.docker.application.pagination;

import java.util.List;

/**
 * Generic pagination result returned by application layer.
 */
public record PageResult<T>(List<T> content, int page, int size, long totalElements) {

    public int totalPages() {
        if (size <= 0)
            return 0;
        return (int) ((totalElements + size - 1) / size);
    }

    public boolean hasNext() {
        return page + 1 < totalPages();
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public <R> PageResult<R> map(java.util.function.Function<T, R> mapper) {
        List<R> mapped = content.stream().map(mapper).toList();
        return new PageResult<>(mapped, page, size, totalElements);
    }
}
