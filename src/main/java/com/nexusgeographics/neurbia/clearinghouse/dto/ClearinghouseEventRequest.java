package com.nexusgeographics.neurbia.clearinghouse.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record ClearinghouseEventRequest(
        @NotNull UUID eventId,
        @NotBlank String eventType,
        @NotBlank String entityId,
        @NotBlank String entityType,
        @NotBlank String connectorId,
        @NotNull Instant occurredAt,
        @NotNull JsonNode snapshot
) {
}
