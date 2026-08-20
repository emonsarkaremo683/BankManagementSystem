package com.elitetech_inc.ensarkbank.auth_management.auth.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "token_blacklist")
@Data
public class TokenBlacklist extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String token;

    private String email;

    private LocalDateTime expiresAt;
}
