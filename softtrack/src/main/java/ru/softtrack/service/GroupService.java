package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softtrack.dto.response.GroupResponse;
import ru.softtrack.dto.response.LdapGroupResponse;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.repository.GroupRepository;
import ru.softtrack.utils.LdapUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final LdapTemplate ldapTemplate;
    @Value("${ldap.group.academic-group-description}")
    private String academicDescription;
    @Value("${ldap.group.english-group-description}")
    private String englishDescription;
    @Value("${ldap.group.speciality-group-description}")
    private String specialityDescription;
    @Value("${ldap.group.search-base}")
    private String groupBase;
    @Value("${spring.ldap.base}")
    private String ldapBase;
    @Value("${ldap.group.filter}")
    private String groupFilter;
    private final LdapUserService ldapUserService;
    private final Map<String, String> cnToDnCache = new ConcurrentHashMap<>();

    @Transactional(readOnly = true)
    public List<GroupResponse> getAllGroups() {
        return groupRepository.findAll()
                .stream().map(group ->
                        new GroupResponse(group.getId(),group.getName())).collect(Collectors.toList());
    }

    public List<LdapGroupResponse> getAcademicGroups() {
        List<LdapGroupResponse> groups = ldapTemplate.search(
                query()
                        .base(groupBase)
                        .where("objectClass").is(groupFilter)
                        .and("description").is(academicDescription),
                (ContextMapper<LdapGroupResponse>) ctx -> {
                    DirContextOperations context = (DirContextOperations) ctx;

                    String cn = context.getStringAttribute("cn");
                    String dn = context.getDn().toString();
                    cnToDnCache.put(cn, dn);
                    return new LdapGroupResponse(cn, academicDescription);
                }
        );
        log.info("Found {} academic groups", groups.size());
        return groups;
    }

    public List<LdapGroupResponse> getOtherGroups() {
        List<LdapGroupResponse> groups = ldapTemplate.search(
                query()
                        .base(groupBase)
                        .where("objectClass").is(groupFilter)
                        .and(query()
                                .where("description").is(englishDescription)
                                .or("description").is(specialityDescription)),
                (ContextMapper<LdapGroupResponse>) ctx -> {
                    DirContextOperations context = (DirContextOperations) ctx;
                    String cn = context.getStringAttribute("cn");
                    String dn = context.getDn().toString();
                    String desc = context.getStringAttribute("description");
                    cnToDnCache.put(cn, dn);
                    return new LdapGroupResponse(cn, desc);
                }
        );
        log.info("Found {} other groups", groups.size());
        return groups;
    }

    public List<UserResponse> getIntersection(String cn1, String cn2) {
        StringBuilder filter = new StringBuilder("(&(objectClass=person)");
        if (cn1 != null) filter.append("(memberOf=").append(buildDn(cn1)).append(")");
        if (cn2 != null) filter.append("(memberOf=").append(buildDn(cn2)).append(")");
        filter.append(")");

        log.debug("Intersection filter: {}", filter);

        return ldapTemplate.search(
                query().filter(filter.toString()),
                (AttributesMapper<UserResponse>) attrs -> {
                    return new UserResponse(
                            LdapUtils.getAttribute(attrs,"uid"),
                            LdapUtils.getAttribute(attrs,"givenName"),
                            LdapUtils.getAttribute(attrs,"sn")
                    );
                }
        );
    }
//
//    private AttributesMapper<UserResponse> userAttributesMapper() {
//        return attrs -> new UserResponse(
//                LdapUtils.getAttribute(attrs, "uid"),
//                LdapUtils.getAttribute(attrs,"givenName"),
//                LdapUtils.getAttribute(attrs,"sn"));
//    }

    private String buildDn(String cn) {
        return "cn=" + cn + "," + groupBase + "," + ldapBase;
    }
}