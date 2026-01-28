package fr.univtln.bruno.demos.docker.adapter.persistence;

import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;

public final class PageRequestMapper {

    private PageRequestMapper() {}

    public static jakarta.data.page.PageRequest toJakarta(PageRequest request) {
        return jakarta.data.page.PageRequest.ofPage(request.page() + 1, request.size(), request.requestTotal());
    }
}
