package fr.univtln.bruno.demos.docker.adapter.persistence;

import fr.univtln.bruno.demos.docker.application.pagination.PageResult;
import jakarta.data.page.PageRequest;
import jakarta.data.page.Page;

import java.util.List;
import java.util.function.Function;

public final class PageMapper {

        private PageMapper() {
        }

        public static <E, D> PageResult<D> map(Page<E> entityPage, Function<E, D> mapper) {

                List<D> content = entityPage.content()
                                .stream()
                                .map(mapper)
                                .toList();

                PageRequest pageRequest = entityPage.pageRequest();

                long total = entityPage.hasTotals()
                                ? entityPage.totalElements()
                                : -1L;

                int page = pageRequest.page() > Integer.MAX_VALUE
                                ? 0
                                : (int) pageRequest.page();

                return new PageResult<>(
                                content,
                                page,
                                pageRequest.size(),
                                total);
        }
}
