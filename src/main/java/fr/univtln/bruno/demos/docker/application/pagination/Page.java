package fr.univtln.bruno.demos.docker.application.pagination;

import java.util.List;

/**
 * Generic pagination record to encapsulate paginated results.
 *
 * @param content       List of items on the current page.
 * @param page          Current page number (0-based).
 * @param size          Number of items per page.
 * @param totalElements Total number of items across all pages.
 * @param <T>           Type of the items in the page.
 */
public record Page<T>(List<T> content, int page, int size, long totalElements) {

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
}
