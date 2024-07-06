package com.its.test.service;

import com.its.test.dao.AccountRepository;
import com.its.test.dao.UserRepository;
import com.its.test.entity.Account;
import com.its.test.entity.User;
import com.its.test.exception.BadRequestException;
import com.its.test.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;


    // API 1
    public User createUser(User user) {
        if (user.getAccounts() == null || user.getAccounts().isEmpty()) {
            throw new IllegalArgumentException("User must have at least one account.");
        }

        String username = user.getUsername().replace(" ", "");
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new UserAlreadyExistsException("Username " + username + " already exists.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setUsername(username);
        user.setPassword(encodedPassword);
        List<Account> accounts = filterExistingAccounts(user.getAccounts());
        if(accounts.isEmpty()) {
           throw new BadRequestException("Account associated with other user.");
        }
        accounts.forEach(account -> {account.setUser(user); account.setLastTxnDate(new Date());});
        return userRepository.save(user);
    }

    public List<Account> filterExistingAccounts(List<Account> accounts) {
        return accounts.stream()
                .filter(account -> !accountRepository.existsByAccountNum(account.getAccountNum()))
                .collect(Collectors.toList());
    }

}
