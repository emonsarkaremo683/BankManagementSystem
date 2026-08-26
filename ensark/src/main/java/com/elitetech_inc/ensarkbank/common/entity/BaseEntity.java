package com.elitetech_inc.ensarkbank.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

/**
 * Base entity providing common fields (id, timestamps, optimistic lock version)
 * for all JPA entities in the application.
 *
 * The {@code @Version} field enables optimistic concurrency control: JPA will
 * reject any UPDATE whose version does not match the value last read from the
 * database, preventing lost-update race conditions.
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
