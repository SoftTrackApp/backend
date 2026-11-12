package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.softtrack.dto.LdapUserDto;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.exception.AccessDeniedException;
import ru.softtrack.repository.RoleRepository;
import ru.softtrack.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LdapAuthenticationProvider implements AuthenticationProvider {

    private final LdapUserService ldapUserService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String uid = authentication.getName();
        String password = Objects.requireNonNull(authentication.getCredentials()).toString();

        boolean authenticated = ldapUserService.authenticate(uid, password);
        if (!authenticated) {
            throw new BadCredentialsException("Invalid login or password");
        }
        LdapUserDto ldapUser = ldapUserService.findUserByUid(uid);

        if (ldapUser == null) {
            throw new BadCredentialsException("Invalid login or password");
        }

        if (!ldapUserService.isRealUser(ldapUser)) {
            throw new AccessDeniedException();
        }

        UserEntity user = userService.findOrCreateUser(uid, ldapUser);

        List<GrantedAuthority> authorities = user.getRole().getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}