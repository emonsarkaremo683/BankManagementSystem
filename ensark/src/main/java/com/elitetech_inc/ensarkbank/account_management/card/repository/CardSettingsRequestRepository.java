package com.elitetech_inc.ensarkbank.account_management.card.repository;

import com.elitetech_inc.ensarkbank.account_management.card.entity.CardSettingsRequest;
import com.elitetech_inc.ensarkbank.common.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardSettingsRequestRepository extends JpaRepository<CardSettingsRequest, Long> {
    List<CardSettingsRequest> findByCardIdAndStatus(Long cardId, RequestStatus status);
    List<CardSettingsRequest> findByStatus(RequestStatus status);
    List<CardSettingsRequest> findByRequestedById(Long userId);
}
