package org.ashok.journal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ashok.journal.model.JournalEntry;

@ApplicationScoped
public class JournalRepository implements PanacheRepository<JournalEntry> {
}
