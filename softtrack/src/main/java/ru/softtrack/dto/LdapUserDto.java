package ru.softtrack.dto;

public record LdapUserDto(String uid,
                          String sn,
                          String givenName,
                          String employeeType) {
}
