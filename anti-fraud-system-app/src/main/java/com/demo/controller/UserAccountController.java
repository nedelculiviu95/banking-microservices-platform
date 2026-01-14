package com.demo.controller;

import com.demo.pojo.CreateUserRequest;
import com.demo.pojo.CreateUserResponse;
import com.demo.pojo.DeleteUserResponse;
import com.demo.pojo.UpdateUserRequest;
import com.demo.entity.UserAccount;
import com.demo.service.UserAccountService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;

    UserAccountController(UserAccountService userAccountService, PasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/api/auth/user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest user) {
        UserAccount account = this.userAccountService.createNewUser(user.username(), passwordEncoder.encode(user.password()), user.name(), user.role());

        return ResponseEntity.status(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateUserResponse(account.getAccountId(), account.getName(), account.getUsername(), account.getRole()));
    }

    @PutMapping(path = "/api/auth/role")
    public ResponseEntity<CreateUserResponse> updateUser(@RequestBody UpdateUserRequest user) {
        if (!Objects.equals(user.role(), "SUPPORT") && !Objects.equals(user.role(), "MERCHANT")) {
            return ResponseEntity.badRequest().build();
        }

        UserAccount account = this.userAccountService.updateRoleByUsername(user.username(), user.role());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CreateUserResponse(account.getAccountId(), account.getName(), account.getUsername(), account.getRole()));
    }

    @GetMapping(path = "/api/auth/list")
    public ResponseEntity<List<UserAccount>> readAllUsers() {
        List<UserAccount> accounts = this.userAccountService.getAllUsers();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accounts);
    }

    @DeleteMapping(path = "/api/auth/user/{username}")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable String username) {
        int deletedUsers = this.userAccountService.deleteUser(username);

        if (deletedUsers == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DeleteUserResponse(username, "Deleted successfully!"));
    }
}
