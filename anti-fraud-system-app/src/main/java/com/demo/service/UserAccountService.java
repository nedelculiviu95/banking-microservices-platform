package com.demo.service;

import com.demo.UserAccountAdapter;
import com.demo.entity.UserAccount;
import com.demo.repo.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * We don't need to check the password or roles here because Spring Security handles that on its own.
 * Our job is just to return the UserDetails object that contains the user's data.
 * We create an instance of our implementation of the UserDetails interface and return it to the caller
 */
@Service
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository repo;

    public UserAccountService(UserAccountRepository repo) {
        this.repo = repo;
    }

    public List<UserAccount> getAllUsers() {
        return repo.findAll();
    }

    /**
     * When a user tries to log in using a login and a password, Spring Security calls the loadUserByUsername method to get the user's data
     * If the username doesn't exist, the method should throw a UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = repo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new UserAccountAdapter(user);
    }

    public UserAccount createNewUser(String username, String password, String name, String role) {
        Optional<UserAccount> user = repo.findByUsername(username);
        if (user.isPresent()) {
            throw new IllegalArgumentException("User already exist");
        }

        return this.repo.save(new UserAccount(username, password, name, role));
    }

    @Transactional
    public UserAccount updateRoleByUsername(String username, String role) {
        UserAccount user = repo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setRole(role);   // No explicit save needed if the entity is managed; changes flush on tx commit.
        return user;
    }

    @Transactional
    public int deleteUser(String username) {
        return this.repo.deleteByUsername(username);
    }
}
