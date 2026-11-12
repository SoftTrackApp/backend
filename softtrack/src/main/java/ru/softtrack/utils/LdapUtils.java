package ru.softtrack.utils;

import lombok.experimental.UtilityClass;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

@UtilityClass
public class LdapUtils {

    public String getAttribute(Attributes attrs, String key) throws NamingException {
        Attribute attr = attrs.get(key);
        return attr != null ? (String) attr.get() : null;
    }
}