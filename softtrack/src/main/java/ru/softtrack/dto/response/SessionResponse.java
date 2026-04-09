package ru.softtrack.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String role;

    public SessionResponse(String id, String firstName, String lastName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
