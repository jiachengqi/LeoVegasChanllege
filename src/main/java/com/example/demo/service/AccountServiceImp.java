package com.example.demo.service;


import com.example.demo.dataObject.Account;
import com.example.demo.exceptions.WalletException;
import com.example.demo.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;

import java.util.List;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private AccountRepo accountRepo;


    @Override
    public Account accountByPK(Long accountId) throws WalletException {
        return accountRepo.findById(accountId).orElseThrow(() -> new WalletException(String.format("account with %d " +
                "not found ", accountId)));
    }

    @Override
    @Transactional
    public Account save(Account account) throws WalletException {
        if (account.getPlayerName() != null) {
            if (account.getPlayerName().length() < 3) {
                throw new WalletException("player name must be more than 3 characters");
            }
            return accountRepo.save(account);
        }
        throw new WalletException("need a name");
    }

    @Override
    @Transactional
    public Account update(Account account, Long id) throws WalletException {
        if (account.getPlayerName() != null) {
            account.setId(id);
            try {
                return accountRepo.save(account);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new WalletException("refresh page");
            }
        }
        throw new WalletException("need a name");
    }

    @Override
    public List<Account> getList() {
        return Lists.newArrayList(accountRepo.findAll());
    }
}
