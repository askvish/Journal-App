package org.ashok.journal;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ashok.journal.model.JournalDTO;
import org.ashok.journal.model.JournalEntry;
import org.ashok.journal.repository.JournalRepository;
import org.ashok.journal.resource.JournalResource;

import java.net.URI;
import java.util.List;

@Path("/entries")
@Blocking
public class JournalPageResource {

    @Inject
    private JournalResource journalResource;

    @Inject
    Template entries;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getEntries() {
        return entries.data("entries", journalResource.getAll());
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JournalEntry> search(@QueryParam("keyword") String keyword) {
        return journalResource.search(keyword);
//        return Response.seeOther(URI.create("/entries")).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response handleForm(
            @FormParam("title") String title,
            @FormParam("content") String content) {
        journalResource.create(new JournalDTO(title, content));

        return Response.seeOther(URI.create("/entries")).build();
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response overrideDelete(@PathParam("id") Long id, @FormParam("_method") String method) {
        if ("DELETE".equalsIgnoreCase(method)) {
            journalResource.delete(id);
            return Response.seeOther(URI.create("/entries")).build();
        }
        return Response.status(400).entity("Unsupported method override").build();
    }
}