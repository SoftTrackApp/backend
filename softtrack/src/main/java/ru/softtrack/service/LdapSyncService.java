package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softtrack.dto.LdapUserDto;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.mapper.LdapUserAttributesMapper;
import ru.softtrack.repository.GroupRepository;
import ru.softtrack.repository.UserRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LdapSyncService {

    private final LdapTemplate ldapTemplate;
    private final LdapUserAttributesMapper ldapUserAttributesMapper;

    //    @Value("${ldap.group.search-bases}")
//    private String[] GROUP_BASES;
    @Value("${ldap.user.search-bases}")
    private String[] USER_BASES;

    private final UserRepository userRepository;
    private final UserService userService;

    private final GroupRepository groupRepository;

    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void syncAll() {
        log.info("Starting LDAP sync...");
        syncUsers();
        //syncGroups();
        log.info("LDAP sync completed");
    }

    private void syncUsers() {
        log.info("Syncing users from LDAP...");

        List<LdapUserDto> ldapUsers = fetchAllLdapUsers();

        int created = 0;
        int updated = 0;

        for (LdapUserDto ldapUser : ldapUsers) {
            UserEntity user = userRepository.findById(ldapUser.uid()).orElse(null);

            if (user == null) {
                userService.createUserFromLdap(ldapUser);
                created++;
            } else {
                if (userService.updateUserFromLdap(user,ldapUser)) {
                    updated++;
                }
            }
        }
        log.info("Users sync completed: {} created, {} updated", created, updated);
    }

    private List<LdapUserDto> fetchAllLdapUsers() {
        Map<String, LdapUserDto> uniqueUsers = new HashMap<>();
        int total = 0;

        for (String base : USER_BASES) {
            try {
                List<LdapUserDto> users = ldapTemplate.search(
                        base,
                        "(objectClass=person)",
                        ldapUserAttributesMapper
                );
                total += users.size();
                for (LdapUserDto user : users) {
                    uniqueUsers.putIfAbsent(user.uid(), user);
                }
            } catch(Exception e) {
                log.warn("Failed to search in {}: {}", base, e.getMessage());
            }
        }
        log.info("Total users found: {}, unique: {}",total,uniqueUsers.size());
        return new ArrayList<>(uniqueUsers.values());
    }

//    private void syncGroups() {
//        log.info("Syncing groups from LDAP...");
//
//        List<String> ldapGroupNames = fetchAllLdapGroupNames();
//        List<Group> existingLdapGroups = groupRepository.findBySource(GroupSource.LDAP);
//
//        for (String groupName : ldapGroupNames) {
//            Group group = groupRepository.findByNameAndSource(groupName,GroupSource.LDAP)
//                    .orElseGet(() -> {
//                        Group newGroup = new Group();
//                        newGroup.setSource(GroupSource.LDAP);
//                        newGroup.setName(groupName);
//                        return newGroup;
//                    });
//            groupRepository.save(group);
//        }
//
//        for (Group group : existingLdapGroups) {
//            if (!ldapGroupNames.contains(group.getName())) {
//                log.info("Deleting obsolete LDAP group: {}", group.getName());
//                groupRepository.delete(group);
//            }
//        }
//
//        log.info("Groups sync completed: {} LDAP groups in database", ldapGroupNames.size());
//    }
//
//    private List<String> fetchAllLdapGroupNames() {
//        Set<String> allGroupNames = new HashSet<>();
//        for (String base : GROUP_BASES) {
//            List<String> groups = ldapTemplate.search(
//                    base,
//                    "(objectClass=groupOfNames)",
//                    (Attributes attrs) -> getAttr(attrs, "cn")
//            );
//            allGroupNames.addAll(groups);
//        }
//        return new ArrayList<>(allGroupNames);
//    }
//
//    private String getAttr(Attributes attrs, String key) throws NamingException {
//        Attribute attr = attrs.get(key);
//        return attr != null ? (String) attr.get() : null;
//    }
}