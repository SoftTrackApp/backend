package ru.softtrack.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.stereotype.Component;
import ru.softtrack.dto.LdapUserDto;
import ru.softtrack.utils.LdapUtils;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

@Component
@RequiredArgsConstructor
public class LdapUserAttributesMapper implements AttributesMapper<LdapUserDto> {

    public LdapUserDto mapFromAttributes(Attributes attributes) throws NamingException {
        return new LdapUserDto(
                LdapUtils.getAttribute(attributes,"uid"),
                LdapUtils.getAttribute(attributes,"sn"),
                LdapUtils.getAttribute(attributes,"givenName"),
                LdapUtils.getAttribute(attributes,"employeeType")
        );
    }
}