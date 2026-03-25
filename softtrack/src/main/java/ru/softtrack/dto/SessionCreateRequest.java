package ru.softtrack.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionCreateRequest {

    String login;
    String password;
}
