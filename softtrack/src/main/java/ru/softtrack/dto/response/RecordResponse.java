package ru.softtrack.dto.response;

import java.time.Instant;

public record RecordResponse (
        Integer id,
        String title,
        Integer behaviorId,
        String comment,
        Instant createdAt
){}