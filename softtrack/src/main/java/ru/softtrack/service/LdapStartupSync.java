package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LdapStartupSync {

    private final LdapSyncService ldapSyncService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        ldapSyncService.syncAll();
    }
}
