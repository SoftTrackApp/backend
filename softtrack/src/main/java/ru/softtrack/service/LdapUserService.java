package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import ru.softtrack.dto.LdapUserDto;
import ru.softtrack.mapper.LdapUserAttributesMapper;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LdapUserService {

    private final LdapTemplate ldapTemplate;
    private final LdapUserAttributesMapper ldapUserAttributesMapper;

    @Value("${ldap.user.search-bases}")
    private String[] USER_BASES;

    public boolean authenticate(String uid, String password) {
        for (String searchBase : USER_BASES) {
            try {
                ldapTemplate.authenticate(
                        searchBase,
                        "(uid=" + uid + ")",
                        password
                );
                return true;
            } catch (Exception e) {
                log.warn("Failed to search in {}: {}", searchBase, e.getMessage());
            }
        }
        return false;
    }

    public LdapUserDto findUserByUid(String uid) {
        for (String searchBase : USER_BASES) {
            try {
                List<LdapUserDto> users = ldapTemplate.search(
                        searchBase,
                        "(uid=" + uid + ")",
                        ldapUserAttributesMapper
                );
                if (!users.isEmpty()) {
                    return users.get(0);
                }
            } catch (Exception e) {
                log.warn("Failed to search in {}: {}", searchBase, e.getMessage());
            }
        }
        return null;
    }

    public boolean isRealUser(LdapUserDto ldapUser) {
        String uid = ldapUser.uid();
        if (uid == null) return false;

        if (uid.matches("i\\d{2}s\\d{4}")) {
            return true;
        }

        if (uid.matches("t\\d{3}")) {
            return true;
        }

        if (uid.matches("a\\d{3}")) {
            return true;
        }

        return false;
    }
}