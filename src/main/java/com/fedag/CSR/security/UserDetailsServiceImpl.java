package com.fedag.CSR.security;

import com.fedag.CSR.exception.ObjectNotFoundException;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findBySteamId(username).orElseThrow(() ->
                new ObjectNotFoundException("User doesn't exist."));
        return SecurityUser.fromUserToSecurityUser(user);
    }
}
