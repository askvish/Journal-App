package org.ashok.journal.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ashok.journal.model.JournalDTO;
import org.ashok.journal.model.JournalEntry;
import org.ashok.journal.repository.JournalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Path("/apis/entries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JournalResource {

    @Inject
    private JournalRepository journalRepository;

    @GET
    public List<JournalEntry> getAll() {
        return journalRepository.listAll();
    }


    @GET
    @Path("/{id}")
    public JournalEntry get(@PathParam("id") Long id) {
        return journalRepository.findById(id);
    }

    @POST
    @Transactional
    public Response create(JournalDTO journalDTO) {
        JournalEntry entry = new JournalEntry();
        entry.setTitle(journalDTO.getTitle());
        entry.setContent(journalDTO.getContent());
        journalRepository.persist(entry);
        return Response.status(201).build();
    }

    @GET
    @Path("/search")
    public List<JournalEntry> search(@QueryParam("keyword") String keyword) {
        return this.getAll().stream()
                .filter(entry -> entry.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || entry.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public JournalEntry update(@PathParam("id") Long id, JournalEntry newEntry) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        journalRepository.deleteById(id);
        return Response.noContent().build();
    }
}
