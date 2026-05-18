package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softtrack.dto.LdapUserDto;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.entity.Group;
import ru.softtrack.entity.Role;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.exception.EntityNotFoundException;
import ru.softtrack.repository.GroupRepository;
import ru.softtrack.repository.RoleRepository;
import ru.softtrack.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //private final GroupRepository groupRepository;
    private final LdapUserService ldapUserService;
    private final RoleRepository roleRepository;
    @Value("${ldap.admin-uids}")
    private String[] adminUids;
    @Value("${user.roles.admin}")
    private String adminRole;
    @Value("${user.roles.teacher}")
    private String teacherRole;
    @Value("${user.roles.student}")
    private String studentRole;

    @Transactional(readOnly = true)
    public UserEntity findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class));
    }

//    @Transactional(readOnly = true)
//    public List<UserResponse> getUsersByGroup(Integer groupId) {
//        Group group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new EntityNotFoundException(Group.class));
//
//        return group.getUsers().stream()
//                .map(user -> new UserResponse(user.getId(), user.getFName(), user.getLName()))
//                .collect(Collectors.toList());
//    }

    public UserEntity findOrCreateUser(String uid, LdapUserDto ldapUser) {
        return userRepository.findById(uid)
                .map(existingUser -> {
                    updateUserAttributes(existingUser, ldapUser);
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> createUserFromLdap(ldapUser));
    }

    public UserEntity findOrCreateUser(String uid) {
        return userRepository.findById(uid)
                .orElseGet(() -> {
                    LdapUserDto ldapUser = ldapUserService.findUserByUid(uid);
                    if (ldapUser == null) {
                        throw new EntityNotFoundException(UserEntity.class);
                    }
                    return createUserFromLdap(ldapUser);
                });
    }

    @Transactional
    public UserEntity createUserFromLdap(LdapUserDto ldapUser) {
        String roleName = determineRole(ldapUser);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException(Role.class));

        UserEntity newUser = new UserEntity();
        newUser.setId(ldapUser.uid());
        newUser.setFName(ldapUser.givenName());
        newUser.setLName(ldapUser.sn());
        newUser.setRole(role);

        return userRepository.save(newUser);
    }

    public boolean updateUserFromLdap(UserEntity user, LdapUserDto ldapUser) {
        boolean changed = false;
        if(ldapUser.givenName() != null && !ldapUser.givenName().equals(user.getFName())) {
            user.setFName(ldapUser.givenName());
            changed = true;
        }
        if(ldapUser.sn() != null && !ldapUser.sn().equals(user.getLName())) {
            user.setLName(ldapUser.sn());
            changed = true;
        }

        if (changed) {
            userRepository.save(user);
        }
        return changed;
    }

    private String determineRole(LdapUserDto ldapUser) {
        String employeeType = ldapUser.employeeType();
        String userUid = ldapUser.uid();

        for(String uid : adminUids) {
            if (userUid.equals(uid)) {
                return adminRole;
            }
        }

        if("teacher".equals(employeeType)) {
            return teacherRole;
        }

        if("student".equals(employeeType)) {
            return studentRole;
        }
        return studentRole;
    }


    public void updateUserAttributes(UserEntity user, LdapUserDto ldapUser) {
        boolean changed = false;

        if (user.getFName() == null || !user.getFName().equals(ldapUser.givenName())) {
            user.setFName(ldapUser.givenName());
            changed = true;
        }
        if (user.getLName() == null || !user.getLName().equals(ldapUser.sn())) {
            user.setLName(ldapUser.sn());
            changed = true;
        }

        String expectedRole = determineRole(ldapUser);
        if (!user.getRole().getName().equals(expectedRole)) {
            Role newRole = roleRepository.findByName(expectedRole)
                    .orElseThrow(() -> new EntityNotFoundException(Role.class));
            user.setRole(newRole);
            changed = true;
        }

        if (changed) {
            userRepository.save(user);
        }
    }
}