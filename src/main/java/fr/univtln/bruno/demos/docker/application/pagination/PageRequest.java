package fr.univtln.bruno.demos.docker.application.pagination;

import java.util.List;

public record PageRequest(
                int page,
                int size,
                boolean requestTotal,
                String search,
                List<SortRequest> sort) {

        public static PageRequest ofPage(int page, int size) {
                return new PageRequest(page, size, true, null, List.of());
        }

        public static PageRequest ofPage(int page, int size, List<SortRequest> sort) {
                return new PageRequest(page, size, true, null, sort);
        }
}
