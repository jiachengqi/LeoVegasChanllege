package com.example.demo.repo;

import com.example.demo.dataObject.Account;
import com.example.demo.dataObject.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepo extends CrudRepository<Account, Long> {
    Optional<Account> getByPlayerName(String name);
}
