package com.its.test.dao;

import com.its.test.entity.Account;
import com.its.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByAccountNum(Long accountNum);

    List<Account> findByUserAndStatusOrderByLastTxnDateDesc(User user, String status);

    boolean existsByAccountNum(Long accountNum);

}
