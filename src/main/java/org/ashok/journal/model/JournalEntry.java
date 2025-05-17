package org.ashok.journal.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "journals")
public class JournalEntry extends PanacheEntity {

    private String title;

    @Column(columnDefinition = "TEXT", length = 10000)
    private String content;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
