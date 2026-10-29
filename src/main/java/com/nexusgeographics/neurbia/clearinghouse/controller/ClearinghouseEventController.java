package com.nexusgeographics.neurbia.clearinghouse.controller;

import com.nexusgeographics.neurbia.clearinghouse.dto.ClearinghouseEventRequest;
import com.nexusgeographics.neurbia.clearinghouse.dto.ClearinghouseEventResponse;
import com.nexusgeographics.neurbia.clearinghouse.service.ClearinghouseEventService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clearinghouse/events")
public class ClearinghouseEventController {
    private static final Logger log = LoggerFactory.getLogger(ClearinghouseEventController.class);

    private final ClearinghouseEventService eventService;

    public ClearinghouseEventController(ClearinghouseEventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ClearinghouseEventRequest request) {
        log.info("clearinghouse_event_received eventId={} eventType={} entityId={} entityType={}",
                request.eventId(), request.eventType(), request.entityId(), request.entityType());
        var saved = eventService.persist(request);
        log.info("clearinghouse_event_accepted id={}", saved.getId());
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<ClearinghouseEventResponse>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ClearinghouseEventResponse> getById(@PathVariable UUID eventId) {
        return ResponseEntity.ok(eventService.getById(eventId));
    }

    @GetMapping("/transfer/{transferId}")
    public ResponseEntity<List<ClearinghouseEventResponse>> getByTransfer(@PathVariable String transferId) {
        return ResponseEntity.ok(eventService.getByTransfer(transferId));
    }

    @GetMapping("/negotiation/{negotiationId}")
    public ResponseEntity<List<ClearinghouseEventResponse>> getByNegotiation(@PathVariable String negotiationId) {
        return ResponseEntity.ok(eventService.getByNegotiation(negotiationId));
    }
}
