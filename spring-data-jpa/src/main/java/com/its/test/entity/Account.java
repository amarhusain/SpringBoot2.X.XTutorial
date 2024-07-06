package com.its.test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acc_id")
    private Integer accountId;

    @Column(name = "account_num" )
    private Long accountNum;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "last_txn_amt")
    private Long lastTxnAmt;

    @Column(name = "last_txn_dt")
    private Date lastTxnDate;

    @Column(name = "status")
    private String status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getLastTxnAmt() {
        return lastTxnAmt;
    }

    public void setLastTxnAmt(Long lastTxnAmt) {
        this.lastTxnAmt = lastTxnAmt;
    }

    public Date getLastTxnDate() {
        return lastTxnDate;
    }

    public void setLastTxnDate(Date lastTxnDate) {
        this.lastTxnDate = lastTxnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

