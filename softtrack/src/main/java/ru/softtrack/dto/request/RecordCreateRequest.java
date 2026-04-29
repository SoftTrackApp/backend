package ru.softtrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecordCreateRequest (
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Receiver ID is required")
        String receiverId,
        @NotNull(message = "Behavior ID is required")
        Integer behaviorId,
        String comment
){}
