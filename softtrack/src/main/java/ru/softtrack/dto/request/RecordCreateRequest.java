package ru.softtrack.dto.request;

public record RecordCreateRequest (
        String title,
        String receiverId,
        Integer behaviorId,
        String comment
){}
