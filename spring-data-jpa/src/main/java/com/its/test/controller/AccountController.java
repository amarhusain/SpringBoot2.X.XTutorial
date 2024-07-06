package com.its.test.controller;

import com.its.test.entity.Account;
import com.its.test.security.JwtTokenUtil;
import com.its.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // API 2
    @GetMapping("/active")
    public List<Account> getActiveAccounts(@CookieValue(name = "JWT-TOKEN") String jwtToken) {
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return accountService.getActiveAccountsByUsername(username);
    }

    // API 3
    @PostMapping(name = "/add-account")
    public Account addAccountForUser(@CookieValue(name = "JWT-TOKEN")  String jwtToken, @RequestBody Account account) {
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return accountService.addAccountForUser(username, account);
    }

    // API 4
    @GetMapping("/delete")
    public ResponseEntity<Object> deleteAccountByAccNumber(@RequestParam Long accountNum) {
        accountService.deleteAccountByAccountNum(accountNum);
        return new ResponseEntity<>("Account with account number " + accountNum + " deleted successfully", HttpStatus.OK);
    }

    // API 5
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccount(@PathVariable Integer id,  @RequestParam Long lastTxnAmt) {
        Account account = accountService.updateAccountForUser(id, lastTxnAmt);
        return new ResponseEntity<>("Account with account number "+ account.getAccountNum()+" updated successfully", HttpStatus.OK);
    }

}

