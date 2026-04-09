package ru.softtrack.dto.response;

import java.time.LocalDateTime;

public record RecordResponse (
        Integer id,
        String title,
        Integer behaviorId,
        String comment,
        LocalDateTime createdAt
){}