package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.service.LdapSyncService;

import java.util.Map;

@RestController
@RequestMapping("/admin/sync")
@PreAuthorize("hasAuthority('manage_groups') && hasAuthority('manage_profiles')")
@RequiredArgsConstructor
public class AdminSyncController {

    private final LdapSyncService ldapSyncService;

    @PostMapping("/ldap")
    public ResponseEntity<?> syncLdap() {
        ldapSyncService.syncAll();
        return ResponseEntity.ok(Map.of("message", "LDAP sync completed"));
    }
}