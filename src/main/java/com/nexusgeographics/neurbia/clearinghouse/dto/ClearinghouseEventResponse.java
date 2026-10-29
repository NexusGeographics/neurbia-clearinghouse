package com.nexusgeographics.neurbia.clearinghouse.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.nexusgeographics.neurbia.clearinghouse.entity.ClearinghouseEvent;

import java.time.Instant;
import java.util.UUID;

public record ClearinghouseEventResponse(
        UUID eventId,
        String eventType,
        String entityId,
        String entityType,
        String connectorId,
        Instant occurredAt,
        Instant receivedAt,
        JsonNode snapshot
) {
    public static ClearinghouseEventResponse from(ClearinghouseEvent event) {
        return new ClearinghouseEventResponse(
                event.getId(),
                event.getEventType(),
                event.getEntityId(),
                event.getEntityType(),
                event.getConnectorId(),
                event.getOccurredAt(),
                event.getReceivedAt(),
                event.getPayload()
        );
    }
}
