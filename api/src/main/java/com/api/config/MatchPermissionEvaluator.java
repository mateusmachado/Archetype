package com.api.config;

import com.api.repository.RoleRepository;
import com.api.repository.UserRepository;
import com.api.model.Role;
import com.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class MatchPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        String userName = authentication.getName();
        User user = userRepository.findByUserName(userName);
        if (user == null)
            throw new UsernameNotFoundException("not found");
        // admin with id == 1
        if (user.isAdmin()) return true;

        Role role = roleRepository.find(user.getRoleId());
        if (role == null) return false;

        return role.hasPermission(targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
