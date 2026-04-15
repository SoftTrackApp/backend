package ru.softtrack.dto.response;
import java.util.List;

public record BehaviorSetResponse(Integer id, String name, List<BehaviorResponse> behaviors) {
}
