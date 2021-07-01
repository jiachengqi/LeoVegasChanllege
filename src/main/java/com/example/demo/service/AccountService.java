package com.example.demo.service;

import com.example.demo.dataObject.Account;
import com.example.demo.exceptions.WalletException;

public interface AccountService extends PlayerService<Account> {
    Account accountByPK(Long accountId) throws WalletException;
}
