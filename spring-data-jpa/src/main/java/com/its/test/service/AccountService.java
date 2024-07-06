package com.its.test.service;

import com.its.test.dao.AccountRepository;
import com.its.test.dao.UserRepository;
import com.its.test.entity.Account;
import com.its.test.entity.User;
import com.its.test.exception.BadRequestException;
import com.its.test.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService  {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // API 2
    @Cacheable(value = "accountsCache", key = "#username")
    public List<Account> getActiveAccountsByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("No account found for username " + username);
        }
        return accountRepository.findByUserAndStatusOrderByLastTxnDateDesc(user, "active");
    }

    // API 3
    public Account addAccountForUser(String username, Account account) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // Handle case where user doesn't exist
            throw new BadRequestException("User not found with username: " + username);
        }
        account.setUser(user);
        return accountRepository.save(account);
    }

    // API 4
    public void deleteAccountByAccountNum(Long accountNum) {
        Account account = accountRepository.findByAccountNum(accountNum);
        if (account != null) {
            accountRepository.delete(account);
        } else {
            throw new ResourceNotFoundException("No account found with account number: " + accountNum);
        }
    }


    // API 5
    public Account updateAccountForUser(Integer accountId, Long lastTxnAmt){
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        // Check if account exists
        Account account = optionalAccount.orElseThrow(() -> new BadRequestException("Account not found for given id: " + accountId));
        account.setLastTxnAmt(lastTxnAmt);
        account.setLastTxnDate(new Date());

        return accountRepository.save(account);
    }

}
