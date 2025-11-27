package com.iglo.trabea.auth;

import com.iglo.trabea.user.User;
import com.iglo.trabea.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String workEmail) throws UsernameNotFoundException {
        User user = userRepository.findByWorkEmail(workEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + workEmail));
        return new AuthUserDetails(user);
    }

    public UserDetails loadUserByUsernameAndRole(String workEmail, String roleName) throws UsernameNotFoundException {
        User user = userRepository.findByWorkEmailAndUserRoles_Role_Name(workEmail, roleName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + workEmail + " and role: " + roleName));
        return new AuthUserDetails(user);
    }
}

