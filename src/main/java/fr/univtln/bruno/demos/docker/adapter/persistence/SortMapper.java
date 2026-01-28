package fr.univtln.bruno.demos.docker.adapter.persistence;

import fr.univtln.bruno.demos.docker.application.pagination.SortRequest;
import jakarta.data.Sort;

import java.util.List;

public final class SortMapper {

    @SuppressWarnings("unchecked")
    public static Sort<? super PersonEntity>[] toJakarta(List<SortRequest> sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            return new Sort[0];
        }

        Sort<? super PersonEntity>[] sorts = new Sort[sortBy.size()];
        for (int i = 0; i < sortBy.size(); i++) {
            SortRequest req = sortBy.get(i);
            sorts[i] = req.direction() == SortRequest.SortDirection.ASC
                    ? Sort.asc(req.property())
                    : Sort.desc(req.property());
        }
        return sorts;
    }
}
