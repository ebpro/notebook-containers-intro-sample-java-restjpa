package fr.univtln.bruno.demos.docker.application.pagination;

public record SortRequest(String property, SortDirection direction) {

    public enum SortDirection {
        ASC, DESC
    }
}
