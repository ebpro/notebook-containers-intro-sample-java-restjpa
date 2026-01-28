package fr.univtln.bruno.demos.docker.adapter.rest;

import fr.univtln.bruno.demos.docker.application.usecase.GetPersonsUseCase;
import fr.univtln.bruno.demos.docker.application.usecase.CreatePersonUseCase;
import fr.univtln.bruno.demos.docker.application.dto.CreatePersonRequest;
import fr.univtln.bruno.demos.docker.application.dto.PersonDto;
import fr.univtln.bruno.demos.docker.application.mapper.PersonDtoMapper;
import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;
import fr.univtln.bruno.demos.docker.application.pagination.PageResult;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import jakarta.data.Sort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import fr.univtln.bruno.demos.docker.application.pagination.SortRequest;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final GetPersonsUseCase getPersonsUseCase;
    private final CreatePersonUseCase createPersonUseCase;
    private final PersonDtoMapper mapper;

    @Inject
    public PersonResource(GetPersonsUseCase getPersonsUseCase,
            CreatePersonUseCase createPersonUseCase,
            PersonDtoMapper mapper) {
        this.getPersonsUseCase = getPersonsUseCase;
        this.createPersonUseCase = createPersonUseCase;
        this.mapper = mapper;
    }

@GET
public PageResult<PersonDto> list(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("size") @DefaultValue("20") int size,
        @QueryParam("sort") String sort) {

    List<SortRequest> sorts = parseSort(sort);

    PageRequest pageRequest = PageRequest.ofPage(page, size);

    PageResult<Person> result = getPersonsUseCase.execute(pageRequest, sorts);

    return result.map(mapper::toDto);
}

    @POST
    public Response create(CreatePersonRequest req) {
        return createPersonUseCase.execute(req)
                .fold(
                        err -> Response.status(PersonErrorMapper.toStatus(err))
                                .entity(new ErrorResponse(err.code(), err.message(), ""))
                                .build(),
                        dto -> {
                            URI location = URI.create("/restjpa/persons/" + dto.id());
                            return Response.created(location).entity(dto).build();
                        });
    }

    // ---------- SORT PARSING ----------
private List<SortRequest> parseSort(String sort) {
    if (sort == null || sort.isBlank()) {
        return List.of();
    }

    String[] entries = sort.split("&");
    List<SortRequest> result = new ArrayList<>();

    for (String entry : entries) {
        String[] parts = entry.split(",");

        if (parts.length != 2) {
            throw badSort();
        }

        String property = parts[0].trim();
        String dir = parts[1].trim().toLowerCase();

        SortRequest.SortDirection direction = switch (dir) {
            case "asc" -> SortRequest.SortDirection.ASC;
            case "desc" -> SortRequest.SortDirection.DESC;
            default -> throw badSort();
        };

        result.add(new SortRequest(property, direction));
    }

    return result;
}



    private WebApplicationException badSort() {
        return new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                .entity("Invalid sort format. Use: ?sort=field,asc or ?sort=field,asc&sort=field2,desc")
                .build());
    }
}
